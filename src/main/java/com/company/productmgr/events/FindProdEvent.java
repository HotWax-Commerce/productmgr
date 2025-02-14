package com.company.productmgr.events;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.*;

import org.apache.ofbiz.base.util.Debug;


public class FindProdEvent {
  public static final String MODULE = FindProdEvent.class.getName();
  public static String findProductMethod(HttpServletRequest request, HttpServletResponse response) {
    Delegator delegator = (Delegator) request.getAttribute("delegator");

    GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
    LocalDispatcher dis = (LocalDispatcher) request.getAttribute("dispatcher");

    String productId = request.getParameter("productId");
    String productName = request.getParameter("productName");
    String productFeatureId = request.getParameter("productFeatureId");
    String price = request.getParameter("price");

    Map<String, Object> product = new HashMap<>();
    product.put("productId", productId);
    product.put("productName", productName);
    product.put("productFeatureId", productFeatureId);
    product.put("price", price);
    try {
      Map<String, Object> result = dis.runSync("findProductService", product);
      List<GenericValue> productList = (List<GenericValue>) result.get("productList");
      request.setAttribute("productList", productList);
    }

    catch (GenericServiceException e) {
      String errMsg = "Unable to run findProductService" + e.toString();
      request.setAttribute("_ERROR_MESSAGE_", errMsg);
      return "error";
    }
    return "success";
  }
}
