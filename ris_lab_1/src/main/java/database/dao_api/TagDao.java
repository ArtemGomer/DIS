package database.dao_api;

import generated.Tag;

import java.util.List;

public interface TagDao {

    boolean addTagStatement(Tag tag);
    boolean addTagPreparedStatement(Tag tag);
    boolean addTagBatch(List<Tag> tags);

}
