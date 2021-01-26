package DB;

public interface TablesInterface {
    void create();
    void insert(Object obj);
    void update(Object obj, String id);
    void delete(String id);
    String find(Object obj);
}
