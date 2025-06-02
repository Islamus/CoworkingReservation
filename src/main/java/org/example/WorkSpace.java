package org.example;

class WorkSpace {
    String id, type;
    double price;
    boolean available;

    public WorkSpace(String id, String type, double price, boolean available) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.available = available;
    }

    public String toFileString() {
        return "ID: " + id + ", " + "Type: " + type + ", " + "Price: "+ price + ", " + "Status: " + available;
    }

    public static WorkSpace fromFileString(String line) {
        try {
            String[] parts = line.split(",");
            String id = parts[0].split(":")[1].trim();
            String type = parts[1].split(":")[1].trim();
            double price = Double.parseDouble(parts[2].split(":")[1].trim());
            boolean available = Boolean.parseBoolean(parts[3].split(":")[1].trim());
            return new WorkSpace(id, type, price, available);
        } catch (Exception e) {
            throw new IllegalArgumentException("Malformed workspace line: " + line);
        }
    }


}
