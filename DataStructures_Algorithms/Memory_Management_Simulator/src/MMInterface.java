// Noel Willems: MMInterface
// Interface for the MemMgr class. Basically a blueprint. 

public interface MMInterface {
	
	final int size = 0;
	final int id = 0;
	
	void init(int size);

	void malloc(int id, int size) throws MyOutOfMemoryException;

	void free(int id) throws MyInvalidMemoryException;

	void print();
}