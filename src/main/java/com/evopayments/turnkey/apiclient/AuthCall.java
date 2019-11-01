package com.myriadpayments.globalturnkey.apiclient;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.myriadpayments.globalturnkey.apiclient.code.ActionType;
import com.myriadpayments.globalturnkey.apiclient.exception.RequiredParamException;
import com.myriadpayments.globalturnkey.config.ApplicationConfig;

/**
 * Requests authorisation for a payment
 * 
 * @author erbalazs
 *
 */
public class AuthCall extends BaseApiCall {

	public AuthCall(ApplicationConfig config, Map<String, String> inputParams, PrintWriter outputWriter) {
		super(config, inputParams, outputWriter);
	}

	@Override
	protected ActionType getActionType() {
		return ActionType.AUTH;
	}

}
