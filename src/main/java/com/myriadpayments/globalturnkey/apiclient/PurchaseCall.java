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
 * Does an authorize and capture operations at once (and cannot be voided)
 * 
 * @author erbalazs
 *
 */
public class PurchaseCall extends BaseApiCall {

	public PurchaseCall(ApplicationConfig config, Map<String, String> inputParams, PrintWriter outputWriter) {
		super(config, inputParams, outputWriter);
	}

	public PurchaseCall(ApplicationConfig config, Map<String, String> inputParams, PrintWriter outputWriter, String subActionType) {
		super(config, inputParams, outputWriter, subActionType);
	}

	@Override
	protected ActionType getActionType() {
		return ActionType.PURCHASE;
	}

}
