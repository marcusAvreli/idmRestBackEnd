package idmRestBackEnd.idm.connection.def;

public enum DataBaseType {
	DERBY("DERBY"),
	MYSQL("MYSQL"),
	IDM("IDM");
	
    private final String val;       

    private DataBaseType( String s ) {
    	val = s;
    }
    
    public String toString() {
       return this.val;
    }
}