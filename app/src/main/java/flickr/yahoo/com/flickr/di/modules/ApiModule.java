//package flickr.yahoo.com.flickr.di.modules;
//
//
//
//import dagger.Module;
//import dagger.Provides;
//import flickr.yahoo.com.flickr.di.scopes.ApplicationScope;
//import retrofit2.Retrofit;
//
//@Module
//public class ApiModule {
//
//    @Provides @ApplicationScope
//    public OrderDataSource orderApi(
//        @CentralEndpoint Retrofit retrofitCentral,
//        @RegionalEndpoint Retrofit retrofitRegional
//    ) {
//        return new OrderApiMock();
//        //return new OrderApiRetrofit(retrofitCentral, retrofitRegional);
//    }
//
//    @Provides @ApplicationScope
//    public WebSocketApi webSocketApi() {
//        return new WebSocketApiImpl();
//    }
//
//    @Provides @ApplicationScope
//    public StockDataSource stockApi() {
//        return new StockApiMock();
//    }
//
//}