class ShapeDemo
{
    public static double totalArea( Shape [ ] arr )
    {
        double total = 0;
        
        for( Shape s : arr )
            if( s != null )
                total += s.area( );
        
        return total;
    }

    public static double totalArea( java.util.List<? extends Shape> arr )
    {
        double total = 0;
        
        for( Shape s : arr )
            if( s != null )
                total += s.area( );
        
        return total;
    }
    
    public static double totalSemiperimeter( Shape [ ] arr )
    {
        double total = 0;
        
        for( Shape s : arr )
            if( s != null )
                total += s.semiperimeter( );
        
        return total;
    }
    
    public static void printAll( Shape [ ] arr )
    {
        for( Shape s : arr )
            System.out.println( s );
    }

    public static Shape[] arr sort(Shape[] arr) {                                           // quest 1 part II
        Shape[] temp = arr;
        temp.sort((r1, r2) -> r1.compareTo(r2));
        for (Shape shape : temp) {
            System.out.println(shape.area());
        }
        return temp;
    }

    public static void stretchShape(Shape[] arr) {                                          // 1b
        Shape[] temp = arr;
        for (Shape s : temp) {
            if (s instanceof Stretchable) {
                ((Stretchable) s).stretch(2);
                System.out.println(s.area());
            }
        }
    }

    
    public static void main( String [ ] args )
    {
        Shape [ ] a = { new Circle( 2.0 ), new Rectangle( 1.0, 3.0 ),
                        null };                                                             // new Rectangle(4.0, 3.0)
       
        System.out.println( "Total area = " + totalArea( a ) );
        System.out.println( "Total semiperimeter = " + totalSemiperimeter( a ) );
        
        java.util.List<Circle> lst = new java.util.ArrayList<Circle>( );
        lst.add( new Circle( 2.0 ) );
        lst.add( new Circle( 1.0 ) );
        System.out.println( "Total area = " + totalArea( lst ) );

        printAll( a );

        TreeSet<Shape> treeShapeSet = new TreeSet<Shape>((r1, r2) -> r1.compareTo(r2));     // 1c
        for(Shape s : a) {
            treeShapeSet.add(s);
        }
        Iterator iterator = treeShapeSet.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next.area());
        }
    }
}