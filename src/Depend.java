/* Find dependencies between all the Java classes in a directory. Type
java Depend [directory]. */

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.jar.*;
import java.util.concurrent.*;
import java.lang.reflect.*;

class Depend {
    // The classes are represented as a map from a class name to the set of
    // other classes that the class directly references.  This gets reversed
    // into a map from a class name to the set of classes which directly depend
    // on it.  The visited and done sets, and sorted list, are used during
    // topological sorting.
    private Map<String,Set<String>> classes;
    private Map<String,Set<String>> depends;
    private Set<String> visited, done;
    private List<String> sorted;
    private boolean cycleReported;

    public static void main(String[] args) {
        Depend program = new Depend();
        File dir = new File(args.length > 0 ? args[0] : ".");
        try { program.run(dir); }
        catch (Exception e) { throw new Error(e); }
    }

    // Find dependencies in the directory of classes.
    private void run(File dir) throws Exception {
        classes = new TreeMap<String,Set<String>>();
        depends = new TreeMap<String,Set<String>>();
        readDir(dir);
        filter();
        dependencies();
        sort();
        print();
    }

    // Analyse all the class files in the directory
    private void readDir(File dir) throws Exception {
        String[] filenames = dir.list();
        for (String filename: filenames) {
            if (! filename.endsWith(".class")) continue;
            if (filename.contains("$")) continue;
            InputStream is = new FileInputStream(new File(dir, filename));
            Set<String> refs = analyse(is);
            if (refs == null) continue;
            String classname = filename.substring(0, filename.length()-6);
            classes.put(classname, refs);
        }
    }

    // Analyse a class, returning a set of the names of the classes it directly
    // depends on, possibly including itself.
    private Set<String> analyse(InputStream in) throws Exception {
        List<String> list = read(in);
        String superclass = list.get(0);
        Set<String> refs = new TreeSet<String>();
        for (String name: list) {
            if (name.contains("$")) continue;
            name = name.substring(name.indexOf('/')+1);
            refs.add(name);
        }
        return refs;
    }

    // Read in a class file, returning a list of the classes it directly
    // depends on.  The first class in the list is the superclass, and the list
    // may contain duplicates.
    private List<String> read(InputStream is) throws Exception {
        String[] names;
        int[] classes;

        DataInputStream in = new DataInputStream(is);
        in.readInt();
        in.readUnsignedShort();
        in.readUnsignedShort();
        int nConstants = in.readUnsignedShort();
        names = new String[nConstants];
        classes = new int[nConstants];
        int index;
        for (int i=1; i<nConstants; i++) {
            int type = in.readUnsignedByte();
            switch (type) {
            case CLASS: classes[i] = in.readUnsignedShort(); break;
            case FIELDREF:
            case METHODREF:
            case INTERFACE_METHODREF:
            case NAME_AND_TYPE:
                in.readUnsignedShort();
                in.readUnsignedShort();
                break;
            case STRING: in.readUnsignedShort(); break;
            case INTEGER: in.readInt(); break;
            case FLOAT: in.readFloat(); break;
            case LONG: in.readLong(); i++; break;
            case DOUBLE: in.readDouble(); i++; break;
            case UTF8: names[i] = in.readUTF(); break;
            }
        }
        in.readUnsignedShort();
        in.readUnsignedShort();
        String superclass = names[classes[in.readUnsignedShort()]];
        in.close();
        List<String> list = new ArrayList<String>();
        list.add(superclass);
        for (int i=0; i<classes.length; i++)
            if (classes[i] != 0) list.add(names[classes[i]]);
        return list;
    }

    // Define the codes used in class files.
    private static final int
	    UTF8 = 1,
	    INTEGER = 3,
	    FLOAT = 4,
	    LONG = 5,
	    DOUBLE = 6,
	    CLASS = 7,
	    STRING = 8,
	    FIELDREF = 9,
	    METHODREF = 10,
	    INTERFACE_METHODREF = 11,
	    NAME_AND_TYPE = 12;

    // Filter out the references of a class which are not in the set of classes
    // of interest, and filter out classes from their own references.
    private void filter() {
        for (String c: classes.keySet()) {
            Set<String> rs = classes.get(c);
            Iterator<String> it = rs.iterator();
            while (it.hasNext()) {
                String r = it.next();
                if (r.equals(c)) it.remove();
                else if (! classes.containsKey(r)) it.remove();
            }
        }
    }

    // Reverse the class references to find which classes depend on each class
    private void dependencies() {
        for (String name: classes.keySet()) {
            depends.put(name, new HashSet<String>());
        }
        for (String name: classes.keySet()) {
            Set<String> ds = classes.get(name);
            for (String d: ds) {
                depends.get(d).add(name);
            }
        }
    }

    // Do a topological sort, and report any cycles found.
    private void sort() {
        visited = new HashSet<String>();
        done = new HashSet<String>();
        sorted = new ArrayList<String>();
        for (String c: classes.keySet()) {
            if (visited.contains(c)) continue;
            visit(c);
        }
    }

    // Visit one class while sorting.
    private void visit(String c) {
        if (done.contains(c)) return;
        if (! visited.contains(c)) {
            visited.add(c);
            for (String r : classes.get(c)) visit(r);
            done.add(c);
            sorted.add(c);
        }
        else {
            done.add(c);
            if (cycleReported) return;
            System.out.println(
                "*** Dependency cycle involving " + c + " ***");
            cycleReported = true;
        }
    }

    // Print out the classes and their references for debugging.
    private void print() {
        for (String cn: sorted) {
            System.out.print(cn + " ");
            System.out.println(classes.get(cn));
        }
    }
}
