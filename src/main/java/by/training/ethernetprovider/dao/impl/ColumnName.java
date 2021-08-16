package by.training.ethernetprovider.dao.impl;

public class ColumnName {
    //Table contracts
    public static final String CONTRACT_ID_CONTRACT = "id_contract";
    public static final String CONTRACT_START_DATE = "start_date";
    public static final String CONTRACT_END_DATE = "end_date";
    public static final String CONTRACT_DISCOUNT = "discount";
    public static final String CONTRACT_ID_TARIFF = "id_tariff";
    public static final String CONTRACT_IS_ACTIVE = "is_active";
    public static final String CONTRACT_ID_USER = "id_user";

    //Table promotions
    public static final String PROMOTION_ID_PROMOTION = "id_promotion";
    public static final String PROMOTION_NAME = "name";
    public static final String PROMOTION_DESCRIPTION = "description";
    public static final String PROMOTION_DISCOUNT = "discount";
    public static final String PROMOTION_START_DATE = "start_date";
    public static final String PROMOTION_END_DATE = "end_date";

    //Table roles
    public static final String ROLE_ID_ROLE = "id_role";
    public static final String ROLE_ROLE = "role";

    //Table statuses
    public static final String STATUS_ID_STATUS = "id_status";
    public static final String STATUS_STATUS = "status";

    //Table tariffs
    public static final String TARIFF_ID_TARIFF = "id_tariff";
    public static final String TARIFF_NAME = "name";
    public static final String TARIFF_DESCRIPTION = "description";
    public static final String TARIFF_PRICE = "price";
    public static final String TARIFF_IS_ARCHIVE = "is_archive";
    public static final String TARIFF_ID_PROMOTION = "id_promotion";

    //Table users
    public static final String USER_ID_USER = "id_user";
    public static final String USER_NAME = "name";
    public static final String USER_SURNAME = "surname";
    public static final String USER_CITY = "city";
    public static final String USER_ADDRESS = "address";
    public static final String USER_LOGIN = "login";
    public static final String USER_PASSWORD = "password";
    public static final String USER_EMAIL = "email";
    public static final String USER_ID_ROLE = "id_role";
    public static final String USER_BALANCE = "balance";
    public static final String USER_ID_STATUS = "id_status";
}
