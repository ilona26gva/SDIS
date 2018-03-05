package dbs;

import java.util.ArrayList;

public class File {
	String id;	//Hashing.sha256().hashString("[file_name/date_modified/owner/.../file_data]", StandardCharsets.UTF_8).toString();
	ArrayList<Chunk> chunks;
}
