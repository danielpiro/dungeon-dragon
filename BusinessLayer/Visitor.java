package BusinessLayer;

public interface Visitor {
    boolean visit(Player a);

    boolean visit(Enemies a);

    boolean visit(Empty a);

    boolean visit(Wall a);
}
