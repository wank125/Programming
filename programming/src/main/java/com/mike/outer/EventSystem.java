package com.mike.outer;


/**
 * 事件
 */

abstract class Event {
    private Object source;
    private Object arg;
    private long time;

    public Event(Object source) {
        this(source, null);
    }

    public Event(Object source, Object arg) {
        this.source = source;
        this.arg = arg;
        this.time = System.nanoTime();
    }

    public Object getSource() {
        return source;
    }

    public Object getArg() {
        return arg;
    }

    public long getTime() {
        return time;
    }
}

interface ActionListener {
    void doAction(Event e);
}

class ClickEvent extends Event {

    public ClickEvent(Object source) {
        super(source);
    }
}

class MyButton {
    private String name;
    private ActionListener al = null;

    public MyButton() {
        this("NoName");
    }

    public MyButton(String name) {
        this.name = name;
    }

    public void RegisterListener(ActionListener al) {
        this.al = al;
    }

    public void click() {
        if (al != null) {
            al.doAction(new ClickEvent(this));
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

public class EventSystem {
    MyButton mybutton = new MyButton();

    public EventSystem() {
        mybutton.setName("BUtton 1");
        mybutton.RegisterListener(new ActionListener() {
            @Override
            public void doAction(Event e) {
                ClickEvent ce = (ClickEvent) e;
                String name = ((MyButton) ce.getSource()).getName();
                System.out.println(name + "Clicked");
                System.out.println("Event time: ");
                System.out.println(ce.getTime());

            }
        });
    }

    public void run() {
        mybutton.click();
    }

    public static void main(String[] args) {
        EventSystem eventSystem = new EventSystem();
        eventSystem.run();
    }
}
