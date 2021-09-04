package by.training.ethernetprovider.controller.listener;

import by.training.ethernetprovider.controller.LocaleHolder;
import by.training.ethernetprovider.controller.command.AttributeAndParameter;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;

import java.util.Locale;

public class HttpSessionListenerImpl implements jakarta.servlet.http.HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        Locale locale = LocaleHolder.DEFAULT;
        session.setAttribute(AttributeAndParameter.LOCALE, locale);
    }
}
