package com.mike.computer;

public class Mainboard {
    private CPU cpu;
    protected GraphicsCard gCard;

    public void setCpu(CPU cpu) {
        this.cpu = cpu;
    }

    public void setGraphicsCard(GraphicsCard gCard) {
        this.gCard = gCard;
    }

    public void run() {
        cpu.calculate();
        gCard.display();
    }
}
