package org.knowm.xchange.binance;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.account.BinanceAccountInformation;
import org.knowm.xchange.binance.dto.account.DepositAddress;
import org.knowm.xchange.binance.dto.account.DepositList;
import org.knowm.xchange.binance.dto.account.WithdrawList;
import org.knowm.xchange.binance.dto.account.WithdrawRequest;
import org.knowm.xchange.binance.dto.trade.BinanceCancelledOrder;
import org.knowm.xchange.binance.dto.trade.BinanceListenKey;
import org.knowm.xchange.binance.dto.trade.BinanceNewOrder;
import org.knowm.xchange.binance.dto.trade.BinanceOrder;
import org.knowm.xchange.binance.dto.trade.BinanceTrade;
import org.knowm.xchange.binance.dto.trade.OrderSide;
import org.knowm.xchange.binance.dto.trade.OrderType;
import org.knowm.xchange.binance.dto.trade.TimeInForce;
import si.mazi.rescu.ParamsDigest;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface BinanceAuthenticated extends Binance {

  public static final String SIGNATURE = "signature";
  static final String X_MBX_APIKEY = "X-MBX-APIKEY";

  @POST
  @Path("api/v3/order")
  /**
   * Send in a new order
   *
   * @param symbol
   * @param side
   * @param type
   * @param timeInForce
   * @param quantity
   * @param price optional, must be provided for limit orders only
   * @param newClientOrderId optional, a unique id for the order. Automatically generated if not
   *     sent.
   * @param stopPrice optional, used with stop orders
   * @param icebergQty optional, used with iceberg orders
   * @param recvWindow optional
   * @param timestamp
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  BinanceNewOrder newOrder(
      @FormParam("symbol") String symbol,
      @FormParam("side") OrderSide side,
      @FormParam("type") OrderType type,
      @FormParam("timeInForce") TimeInForce timeInForce,
      @FormParam("quantity") BigDecimal quantity,
      @FormParam("price") BigDecimal price,
      @FormParam("newClientOrderId") String newClientOrderId,
      @FormParam("stopPrice") BigDecimal stopPrice,
      @FormParam("icebergQty") BigDecimal icebergQty,
      @FormParam("recvWindow") Long recvWindow,
      @FormParam("timestamp") long timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  @POST
  @Path("api/v3/order/test")
  /**
   * Test new order creation and signature/recvWindow long. Creates and validates a new order but
   * does not send it into the matching engine.
   *
   * @param symbol
   * @param side
   * @param type
   * @param timeInForce
   * @param quantity
   * @param price
   * @param newClientOrderId optional, a unique id for the order. Automatically generated by
   *     default.
   * @param stopPrice optional, used with STOP orders
   * @param icebergQty optional used with icebergOrders
   * @param recvWindow optional
   * @param timestamp
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  Object testNewOrder(
      @FormParam("symbol") String symbol,
      @FormParam("side") OrderSide side,
      @FormParam("type") OrderType type,
      @FormParam("timeInForce") TimeInForce timeInForce,
      @FormParam("quantity") BigDecimal quantity,
      @FormParam("price") BigDecimal price,
      @FormParam("newClientOrderId") String newClientOrderId,
      @FormParam("stopPrice") BigDecimal stopPrice,
      @FormParam("icebergQty") BigDecimal icebergQty,
      @FormParam("recvWindow") Long recvWindow,
      @FormParam("timestamp") long timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  @GET
  @Path("api/v3/order")
  /**
   * Check an order's status.<br>
   * Either orderId or origClientOrderId must be sent.
   *
   * @param symbol
   * @param orderId optional
   * @param origClientOrderId optional
   * @param recvWindow optional
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  BinanceOrder orderStatus(
      @QueryParam("symbol") String symbol,
      @QueryParam("orderId") long orderId,
      @QueryParam("origClientOrderId") String origClientOrderId,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") long timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  @DELETE
  @Path("api/v3/order")
  /**
   * Cancel an active order.
   *
   * @param symbol
   * @param orderId optional
   * @param origClientOrderId optional
   * @param newClientOrderId optional, used to uniquely identify this cancel. Automatically
   *     generated by default.
   * @param recvWindow optional
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  BinanceCancelledOrder cancelOrder(
      @QueryParam("symbol") String symbol,
      @QueryParam("orderId") long orderId,
      @QueryParam("origClientOrderId") String origClientOrderId,
      @QueryParam("newClientOrderId") String newClientOrderId,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") long timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  @GET
  @Path("api/v3/openOrders")
  /**
   * Get all open orders on a symbol.
   *
   * @param symbol optional
   * @param recvWindow optional
   * @param timestamp
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  List<BinanceOrder> openOrders(
      @QueryParam("symbol") String symbol,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") long timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  @GET
  @Path("api/v3/openOrders")
  /**
   * Get all open orders without a symbol.
   *
   * @param symbol
   * @param recvWindow optional
   * @param timestamp mandatory
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  List<BinanceOrder> openOrders(
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") long timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  @GET
  @Path("api/v3/allOrders")
  /**
   * Get all account orders; active, canceled, or filled. <br>
   * If orderId is set, it will get orders >= that orderId. Otherwise most recent orders are
   * returned.
   *
   * @param symbol
   * @param orderId optional
   * @param limit optional
   * @param recvWindow optional
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  List<BinanceOrder> allOrders(
      @QueryParam("symbol") String symbol,
      @QueryParam("orderId") Long orderId,
      @QueryParam("limit") Integer limit,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") long timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  @GET
  @Path("api/v3/account")
  /**
   * Get current account information.
   *
   * @param recvWindow optional
   * @param timestamp
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  BinanceAccountInformation account(
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") long timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  @GET
  @Path("api/v3/myTrades")
  /**
   * Get trades for a specific account and symbol.
   *
   * @param symbol
   * @param limit optional, default 500; max 500.
   * @param fromId optional, tradeId to fetch from. Default gets most recent trades.
   * @param recvWindow optional
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  List<BinanceTrade> myTrades(
      @QueryParam("symbol") String symbol,
      @QueryParam("limit") Integer limit,
      @QueryParam("fromId") Long fromId,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") long timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  @POST
  @Path("wapi/v3/withdraw.html")
  /**
   * Submit a withdraw request.
   *
   * @param asset
   * @param address
   * @param addressTag optional for Ripple
   * @param amount
   * @param name optional, description of the address
   * @param recvWindow optional
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  WithdrawRequest withdraw(
      @FormParam("asset") String asset,
      @FormParam("address") String address,
      @FormParam("addressTag") String addressTag,
      @FormParam("amount") BigDecimal amount,
      @FormParam("name") String name,
      @FormParam("recvWindow") Long recvWindow,
      @FormParam("timestamp") long timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  @GET
  @Path("wapi/v3/depositHistory.html")
  /**
   * Fetch deposit history.
   *
   * @param asset optional
   * @param startTime optional
   * @param endTime optional
   * @param recvWindow optional
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  DepositList depositHistory(
      @QueryParam("asset") String asset,
      @QueryParam("startTime") Long startTime,
      @QueryParam("endTime") Long endTime,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") long timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  @GET
  @Path("wapi/v3/withdrawHistory.html")
  /**
   * Fetch withdraw history.
   *
   * @param asset optional
   * @param startTime optional
   * @param endTime optional
   * @param recvWindow optional
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  WithdrawList withdrawHistory(
      @QueryParam("asset") String asset,
      @QueryParam("startTime") Long startTime,
      @QueryParam("endTime") Long endTime,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") long timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  @GET
  @Path("wapi/v3/depositAddress.html")
  /**
   * Fetch deposit address.
   *
   * @param asset
   * @param recvWindow
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  DepositAddress depositAddress(
      @QueryParam("asset") String asset,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") long timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Returns a listen key for websocket login.
   *
   * @param apiKey the api key
   * @return
   * @throws BinanceException
   * @throws IOException
   */
  @POST
  @Path("/api/v1/userDataStream")
  BinanceListenKey startUserDataStream(@HeaderParam(X_MBX_APIKEY) String apiKey)
      throws IOException, BinanceException;

  /**
   * Keeps the authenticated websocket session alive.
   *
   * @param apiKey the api key
   * @param listenKey the api secret
   * @return
   * @throws BinanceException
   * @throws IOException
   */
  @PUT
  @Path("/api/v1/userDataStream?listenKey={listenKey}")
  Map<?, ?> keepAliveUserDataStream(
      @HeaderParam(X_MBX_APIKEY) String apiKey, @PathParam("listenKey") String listenKey)
      throws IOException, BinanceException;

  /**
   * Closes the websocket authenticated connection.
   *
   * @param apiKey the api key
   * @param listenKey the api secret
   * @return
   * @throws BinanceException
   * @throws IOException
   */
  @DELETE
  @Path("/api/v1/userDataStream?listenKey={listenKey}")
  Map<?, ?> closeUserDataStream(
      @HeaderParam(X_MBX_APIKEY) String apiKey, @PathParam("listenKey") String listenKey)
      throws IOException, BinanceException;
}
