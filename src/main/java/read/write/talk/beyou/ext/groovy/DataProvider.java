package read.write.talk.beyou.ext.groovy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface DataProvider {
    default void validate(DataRequest request){
    }

    default List<String> getDependencies(){
        return Collections.emptyList();
    }

    String getDataName();

    Object handle(DataRequest request, Map<String, Object> working);
}
