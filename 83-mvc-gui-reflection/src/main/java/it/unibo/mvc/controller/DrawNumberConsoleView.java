package it.unibo.mvc.controller;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.api.DrawResult;

public final class DrawNumberConsoleView implements DrawNumberView {

    @Override
    public void setController(final DrawNumberController observer) {

    }

    @Override
    public void start() {
        throw new UnsupportedOperationException("console view started");
    }

    @Override
    public void result(final DrawResult res) {
        throw new UnsupportedOperationException("Result: " + res);
    }
}
