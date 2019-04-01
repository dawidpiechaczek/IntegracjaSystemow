package com.example.dawid.visitwroclove.presenter;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class BasePresenter<T> {
    private Queue<UICommand<T>> commandQueue = new ConcurrentLinkedDeque<>();

    private T view;

    public void attachView(T view) {
        this.view = view;
        UICommand<T> command = commandQueue.poll();
        while (command != null) {
            command.execute(view);
            command = commandQueue.poll();
        }
    }

    public void detachView() {
        this.view = null;
    }

    public T getView() {
        return view;
    }

    public boolean isViewAttached() {
        return view != null;
    }

    protected void send(UICommand<T> command) {
        if (isViewAttached()) {
            command.execute(view);
        }
    }

    protected void sendSticky(UICommand<T> command) {
        if (view == null) {
            commandQueue.add(command);
        } else {
            command.execute(view);
        }
    }

    protected interface UICommand<T> {
        void execute(T ui);
    }
}
