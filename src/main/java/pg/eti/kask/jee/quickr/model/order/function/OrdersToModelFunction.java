package pg.eti.kask.jee.quickr.model.order.function;

import pg.eti.kask.jee.quickr.entity.Order;
import pg.eti.kask.jee.quickr.model.order.OrdersModel;

import java.util.List;
import java.util.function.Function;

public class OrdersToModelFunction implements Function<List<Order>, OrdersModel> {

	@Override
	public OrdersModel apply(List<Order> orders) {
		return OrdersModel.builder()
				.orders(orders.stream()
						.map(order -> OrdersModel.Order.builder()
								.id(order.getId())
								.price(order.getPrice())
								.creationDateTime(order.getCreationDateTime())
								.modificationDateTime(order.getModificationDateTime())
								.version(order.getVersion())
								.build())
						.toList())
				.build();
	}
}
