package ebayPT.exceptions;


/**
 * Exception used to signal that it is not possible to create a new auction
 * with a certain product because that product is either already sold or
 * currently being auctioned.
 * 
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
 */
public class ProductNotAvailable extends Exception {

	private static final long serialVersionUID = 1L;

}
