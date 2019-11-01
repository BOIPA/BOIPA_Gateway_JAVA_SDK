package com.myriadpayments.globalturnkey.web.servlet.sample.b2s;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.utils.URLEncodedUtils;
import org.json.JSONObject;

import com.myriadpayments.globalturnkey.apiclient.ApiCall;
import com.myriadpayments.globalturnkey.apiclient.PurchaseTokenCall;
import com.myriadpayments.globalturnkey.apiclient.exception.GeneralException;
import com.myriadpayments.globalturnkey.apiclient.exception.PostToApiException;
import com.myriadpayments.globalturnkey.apiclient.exception.RequiredParamException;
import com.myriadpayments.globalturnkey.apiclient.exception.TokenAcquirationException;
import com.myriadpayments.globalturnkey.config.ApplicationConfig;
import com.myriadpayments.globalturnkey.web.servlet.sample.s2s.AbstractServlet;

/**
 * Redirect used in Browser-to-Server mode
 * 
 * @author erbalazs
 *
 */
@WebServlet(name = "PaymentRedirect", description = "Redirect used in Browser-to-Server mode", urlPatterns = "/redirectforpurchase")
public class PaymentRedirectServlet extends HttpServlet {

	private final static Logger logger = Logger.getLogger(PaymentRedirectServlet.class.getName());
	
	protected static final String MERCHANT_ID_PROP_KEY = "application.merchantId";
	protected static final String CASHIER_URL_PROP_KEY = "application.cashierUrl";

	protected final ApplicationConfig config;

	public PaymentRedirectServlet() {
		super();
		config = ApplicationConfig.getInstanceBasedOnSysProp();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			
			final Map<String, String> inputParams = AbstractServlet.extractParams(req);
			
			JSONObject jsonObject = new PurchaseTokenCall(config, inputParams, new PrintWriter(System.out, true)).execute();
			
			inputParams.put("merchantId", config.getProperty(MERCHANT_ID_PROP_KEY));
			inputParams.put("token", jsonObject.getString("token"));
			resp.sendRedirect(config.getProperty(CASHIER_URL_PROP_KEY)+ "?" + URLEncodedUtils.format(ApiCall.getForm(inputParams).build(), "UTF-8"));
			
		} catch (RequiredParamException e) {
			
			resp.setStatus(422);
			
			logger.log(Level.INFO, "missing parameters", e);
			
			resp.getWriter().println("missing fields: " + e.getMissingFields());
			
		} catch (TokenAcquirationException e) {
			resp.setStatus(500);
			logger.log(Level.WARNING, "could not acquire token", e);
		} catch (PostToApiException e) {
			resp.setStatus(500);
			logger.log(Level.SEVERE, "outgoing POST failed", e);
		} catch (GeneralException e) {
			resp.setStatus(500);
			logger.log(Level.SEVERE, "general SDK error", e);
		} catch (Exception e) {
			resp.setStatus(500);
			logger.log(Level.SEVERE, "other error", e);
		}
	}

}
