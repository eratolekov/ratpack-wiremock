Steps to reproduce:

1. Run test (`./gradlew test`)

Expected behaviour: successful tests


Actual behaviour:

```
failed test 'ratpack httpclient'

Condition failed with Exception:

response.body.bytes.size() ==  1000
|        |    |
|        |    io.netty.util.IllegalReferenceCountException: refCnt: 0
|        ratpack.http.internal.ByteBufBackedTypedData@24235ca0
ratpack.http.client.internal.DefaultReceivedResponse@5ba76bd9

	at WireMockExampleSpec.ratpack httpclient(WireMockExampleSpec.groovy:51)
Caused by: io.netty.util.IllegalReferenceCountException: refCnt: 0
	at io.netty.buffer.AbstractByteBuf.ensureAccessible(AbstractByteBuf.java:1489)
	at io.netty.buffer.AbstractByteBuf.checkIndex(AbstractByteBuf.java:1418)
	at io.netty.buffer.AbstractByteBuf.checkDstIndex(AbstractByteBuf.java:1444)
	at io.netty.buffer.CompositeByteBuf.getBytes(CompositeByteBuf.java:1025)
	at io.netty.buffer.CompositeByteBuf.getBytes(CompositeByteBuf.java:1987)
	at io.netty.buffer.CompositeByteBuf.getBytes(CompositeByteBuf.java:49)
	at ratpack.bytebuf.ByteBufRef.getBytes(ByteBufRef.java:405)
	at io.netty.buffer.ByteBufUtil.getBytes(ByteBufUtil.java:899)
	at io.netty.buffer.ByteBufUtil.getBytes(ByteBufUtil.java:873)
	at io.netty.buffer.ByteBufUtil.getBytes(ByteBufUtil.java:865)
	at ratpack.http.internal.ByteBufBackedTypedData.getBytes(ByteBufBackedTypedData.java:67)
	... 1 more
```