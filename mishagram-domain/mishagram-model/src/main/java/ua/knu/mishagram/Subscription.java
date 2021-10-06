package ua.knu.mishagram;

public class Subscription {

    private final int whoSubscribedId;
    private final int subscribedToId;

    public Subscription(
        int whoSubscribedId,
        int subscribedToId
    ) {
        this.whoSubscribedId = whoSubscribedId;
        this.subscribedToId = subscribedToId;
    }

    public int getWhoSubscribedId() {
        return whoSubscribedId;
    }

    public int getSubscribedToId() {
        return subscribedToId;
    }
}
