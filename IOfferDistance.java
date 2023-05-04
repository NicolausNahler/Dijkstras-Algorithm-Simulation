package Dijkstra;

/**
 * @author nicolausnahler
 */
public interface IOfferDistance {
    void offerDistance(String node2change, Node newPrevious, int newDistance);
}
