package masem.sample.microservice.order.util;

import java.sql.Timestamp;

public class GetTimestamp {
	public static Timestamp getTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}
}
