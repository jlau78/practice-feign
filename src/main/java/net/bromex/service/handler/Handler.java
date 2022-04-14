package net.bromex.service.handler;

public interface Handler<R> {

    R transform(final String objectId, final Object input) throws ParseException;
}
