public class S_DES_Tablolar {
	
	
    public static final int[] P10 = {3, 5, 2, 7, 4, 10, 1, 9, 8, 6};
    public static final int[] P8  = {6, 3, 7, 4, 8, 5, 10, 9};
    public static final int[] IP  = {2, 6, 3, 1, 4, 8, 5, 7};
    public static final int[] IP_INV = {4, 1, 3, 5, 7, 2, 8, 6};
    public static final int[] EP  = {4, 1, 2, 3, 2, 3, 4, 1};
    public static final int[] P4  = {2, 4, 3, 1};

    public static final int[][] S0 = {
        {1, 0, 3, 2}, 
        {3, 2, 1, 0},
        {0, 2, 1, 3},
        {3, 1, 3, 2}
    };
    
    public static final int[][] S1 = {
        {0, 1, 2, 3},
        {2, 0, 1, 3}, 
        {3, 0, 1, 0}, 
        {2, 1, 0, 3}
    };
}