package nil.ed.test.h2;

/**
 * @author lidelin.
 */
public interface FieldConstants {

    // t_order - payment_order_info
    /**
     * 支付方式.
     */
    String PAY_METHOD = "payMethod";
    /**
     * 支付流水号.
     */
    String OUT_PAYMENT_ID = "outPaymentId";

    // t_order - features
    String PARTNER_ID = "partnerId";


    // t_order - other_order_info
    String LAST_NAME = "lastName";
    String STORE_TYPE = "storeType";
    String MOBILE_COUNTRY_CODE = "mobileCountryCode";
    String HAVE_ACCOUNT = "haveAccount";
    String FIRST_NAME = "firstName";
    String MOBILE_PHONE = "mobilePhone";
    String IDENTITY_NUMBER = "identityNumber";
    String IDENTITY_TYPE = "identityType";
    String PURCHASER_ACCOUNT = "purchaserAccount";
    String STORE_NAME_CN = "storeNameCn";
    String STORE_NAME_EN = "storeNameEn";
    String EMAIL = "email";
    String PURCHASER_CLIENT_TYPE = "purchaserType";
    String PURCHASER_NAME = "name";

    // t_order - delivery_address
    /**
     * 地址类型 ShippingAddress-快递 PickUpAddress-自提.
     */
    String ADDRESS_ID_PATTERN = "\\{.*\"id\":[\"]{0,1}%s[\"]{0,1}[,}].*";

    String ADDRESS_TYPE = "addressType";
    String DELIVERY_RECEIVE_ADDRESS = "receiveAddress";

    // order_line fields　
    String PAY_TIME = "line.pay_time";
    String GMT_CREATE = "line.gmt_create";
    String ENABLE_STATUS = "line.enable_status";
    String PAY_STATUS = "line.pay_status";
    String CREDIT_ORDER_TYPE = "line.credit_order_type";
    String DELIVERY_STATUS = "line.delivery_status";
    String ORDER_CLASSIFY = "line.order_classify";
    String TRADE_ORDER_ID = "line.trade_order_id";

    // ob_item_search fields
    String TENANT_ID = "ob_item_search.tenant_id";
}
