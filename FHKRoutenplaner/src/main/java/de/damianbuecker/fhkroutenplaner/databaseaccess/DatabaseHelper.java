package de.damianbuecker.fhkroutenplaner.databaseaccess;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import de.damianbuecker.fhkroutenplaner.model.HistoryItem;

// TODO: Auto-generated Javadoc
/**
 * The Class DatabaseHelper.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	/** The Constant DATABASE_NAME. */
	private static final String DATABASE_NAME = "database.db";

	/** The Constant DATABASE_VERSION. */
	private static final int DATABASE_VERSION = 1;

	/** The tag data dao. */
	private Dao<Tag, Integer> tagDataDao;

	/** The history item data dao. */
	private Dao<HistoryItem, Integer> historyItemDataDao;

	/** The room data dao. */
	private Dao<Room, Integer> roomDataDao;

	/** The docent data dao. */
	private Dao<Docent, Integer> docentDataDao;

	/** The roomtype data dao. */
	private Dao<Roomtype, Integer> roomtypeDataDao;

	/** The edges data dao. */
	private Dao<Edges, Integer> edgesDataDao;

	/** The roomtype spinner. */
	private Dao<Roomtype, Integer> roomtypeSpinner;

	/** The tag location all. */
	private Dao<Tag, Integer> tagLocationAll;

	/** The room all. */
	private Dao<Room, Integer> roomAll;

	/** The edges all. */
	private Dao<Edges, Integer> edgesAll;

	/** The room spinner. */
	private Dao<Room, Integer> roomSpinner;

	/** The spinner room list. */
	@SuppressWarnings("rawtypes")
	private List spinnerRoomList;

	/** The spinner roomtype list. */
	@SuppressWarnings("rawtypes")
	private List spinnerRoomtypeList = new ArrayList<Integer>();

	/** The query builder. */
	private QueryBuilder<Room, Integer> queryBuilder = null;

	/** The query builder tag. */
	private QueryBuilder<Tag, Integer> queryBuilderTag = null;

	/** The query builder edges. */
	private QueryBuilder<Edges, Integer> queryBuilderEdges = null;

	/** The Tagresult. */
	private List<Tag> Tagresult = null;

	/** The room result. */
	private List<Room> roomResult = null;

	/** The edges result. */
	private List<Edges> edgesResult = null;

	/** The edge result remain. */
	private List<Edges> edgeResultRemain = null;

	/** The edges result destination. */
	private List<Edges> edgesResultDestination = null;

	/**
	 * Instantiates a new database helper.
	 * 
	 * @param context
	 *            the context
	 */
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper#onCreate(android
	 * .database.sqlite.SQLiteDatabase,
	 * com.j256.ormlite.support.ConnectionSource)
	 */
	@Override
	public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
		try {
			TableUtils.dropTable(connectionSource, Tag.class, true);
			TableUtils.dropTable(connectionSource, Room.class, true);
			TableUtils.dropTable(connectionSource, Docent.class, true);
			TableUtils.dropTable(connectionSource, Roomtype.class, true);
			TableUtils.dropTable(connectionSource, Edges.class, true);
			TableUtils.dropTable(connectionSource, HistoryItem.class, true);

			TableUtils.createTable(connectionSource, Tag.class);
			TableUtils.createTable(connectionSource, Room.class);
			TableUtils.createTable(connectionSource, Docent.class);
			TableUtils.createTable(connectionSource, Roomtype.class);
			TableUtils.createTable(connectionSource, Edges.class);
			TableUtils.createTable(connectionSource, HistoryItem.class);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper#onUpgrade(android
	 * .database.sqlite.SQLiteDatabase,
	 * com.j256.ormlite.support.ConnectionSource, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, Tag.class, true);
			TableUtils.dropTable(connectionSource, Room.class, true);
			TableUtils.dropTable(connectionSource, Docent.class, true);
			TableUtils.dropTable(connectionSource, Roomtype.class, true);
			TableUtils.dropTable(connectionSource, Edges.class, true);
			TableUtils.dropTable(connectionSource, HistoryItem.class, true);

			this.onCreate(database, connectionSource);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Delete tag.
	 */
	public void deleteTag() {
		try {

			Dao<Tag, Integer> tagDao = getTagDataDao();
			List<Tag> listTag = tagDao.queryForAll();
			tagDao.delete(listTag);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Delete room.
	 */
	public void deleteRoom() {
		try {
			Dao<Room, Integer> roomDao = getRoomDataDao();
			List<Room> listRoom = roomDao.queryForAll();
			roomDao.delete(listRoom);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Delete roomtype.
	 */
	public void deleteRoomtype() {
		try {
			Dao<Roomtype, Integer> roomtypeDao = getRoomtypeDataDao();
			List<Roomtype> roomtypeList = roomtypeDao.queryForAll();
			roomtypeDao.delete(roomtypeList);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Delete docent.
	 */
	public void deleteDocent() {
		try {
			Dao<Docent, Integer> docentDao = getDocentDataDao();
			List<Docent> listDocent = docentDao.queryForAll();
			docentDao.delete(listDocent);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Delete edges.
	 */
	public void deleteEdges() {
		try {
			Dao<Edges, Integer> edgesDao = getEdgesDataDao();
			List<Edges> listEdges = edgesDao.queryForAll();
			edgesDao.delete(listEdges);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Delete complete database.
	 */
	public void deleteCompleteDatabase() {
		deleteEdges();
		deleteTag();
		deleteRoom();
		deleteRoomtype();
		deleteDocent();
	}

	/**
	 * Gets the tag data dao.
	 * 
	 * @return the tag data dao
	 * @throws SQLException
	 *             the SQL exception
	 */
	public Dao<Tag, Integer> getTagDataDao() throws SQLException {
		if (this.tagDataDao == null) {
			tagDataDao = getDao(Tag.class);
		}

		return this.tagDataDao;
	}

	/**
	 * Gets the room data dao.
	 * 
	 * @return the room data dao
	 * @throws SQLException
	 *             the SQL exception
	 */
	public Dao<Room, Integer> getRoomDataDao() throws SQLException {
		if (this.roomDataDao == null) {
			roomDataDao = getDao(Room.class);
		}

		return this.roomDataDao;
	}

	/**
	 * Gets the docent data dao.
	 * 
	 * @return the docent data dao
	 * @throws SQLException
	 *             the SQL exception
	 */
	public Dao<Docent, Integer> getDocentDataDao() throws SQLException {
		if (this.docentDataDao == null) {
			docentDataDao = getDao(Docent.class);
		}

		return this.docentDataDao;
	}

	/**
	 * Gets the roomtype data dao.
	 * 
	 * @return the roomtype data dao
	 * @throws SQLException
	 *             the SQL exception
	 */
	public Dao<Roomtype, Integer> getRoomtypeDataDao() throws SQLException {
		if (this.roomtypeDataDao == null) {
			roomtypeDataDao = getDao(Roomtype.class);
		}

		return this.roomtypeDataDao;
	}

	/**
	 * Gets the edges data dao.
	 * 
	 * @return the edges data dao
	 * @throws SQLException
	 *             the SQL exception
	 */
	public Dao<Edges, Integer> getEdgesDataDao() throws SQLException {
		if (this.edgesDataDao == null) {
			edgesDataDao = getDao(Edges.class);
		}

		return this.edgesDataDao;
	}

	/**
	 * Gets the history item data dao.
	 * 
	 * @return the history item data dao
	 * @throws SQLException
	 *             the SQL exception
	 */
	public Dao<HistoryItem, Integer> getHistoryItemDataDao() throws SQLException {
		if (this.historyItemDataDao == null) {
			historyItemDataDao = getDao(HistoryItem.class);
		}
		return this.historyItemDataDao;
	}

	/**
	 * Delete tag dao.
	 *
	 * @throws SQLException the SQL exception
	 */
	public void deleteTagDao() throws SQLException {
		if (this.tagDataDao == null) {
			tagDataDao = getDao(Tag.class);
		}

	}

	/**
	 * Gets the roomtype spinner.
	 * 
	 * @return all roomtype data 
	 * @throws SQLException
	 *             the SQL exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getRoomtypeSpinner() throws SQLException {
		if (this.roomtypeSpinner == null) {
			this.roomtypeSpinner = getRoomtypeDataDao();

			for (Roomtype v : roomtypeSpinner) {
				spinnerRoomtypeList.add(String.valueOf(v.getRoomtype_id()) + " " + String.valueOf(v.getDescription()));
			}

		}

		return spinnerRoomtypeList;
	}

	/**
	 * Gets the room spinner.
	 * 
	 * @param roomid
	 *            Chosen roomtypeID 
	 * @param startID
	 *             the id of the starting node
	 * @return all room data
	 * @throws SQLException
	 *             the SQL exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getRoomSpinner(Integer roomid, String startID) throws SQLException {		
		if (this.roomSpinner == null) {
			this.roomSpinner = this.getRoomDataDao();
		}

		List<Tag> startTagList = this.getTagById(startID);
		Integer bufferRoomIdFromStartTag = startTagList.get(0).getRoom_ID();		

		this.spinnerRoomList = new ArrayList<Integer>();
		this.queryBuilder = roomSpinner.queryBuilder();
		if(bufferRoomIdFromStartTag == null){
			
			this.queryBuilder.where().eq(Room.ROOMTYPE_ID, roomid);
			
		}else{
			this.queryBuilder.where().eq(Room.ROOMTYPE_ID, roomid).and().not().like(Tag.ROOM_ID, bufferRoomIdFromStartTag);
		}
		
		PreparedQuery<Room> preparedQuery = queryBuilder.prepare();
		List<Room> RoomList = roomSpinner.query(preparedQuery);
		for (Room v : RoomList) {

			spinnerRoomList.add(String.valueOf(v.getRoom_id()) + " " + String.valueOf(v.getDescription()));
		}
		RoomList = null;
		roomSpinner = null;

		return spinnerRoomList;

	}

	/**
	 * Gets the tag by id.
	 * 
	 * @param ID
	 *            id of the tag object
	 * @return tagobject
	 * @throws SQLException
	 *             the SQL exception
	 */
	public List<Tag> getTagById(String ID) throws SQLException {

		if (this.tagLocationAll == null) {
			this.tagLocationAll = this.getTagDataDao();
		}

		this.queryBuilderTag = tagLocationAll.queryBuilder();
		this.queryBuilderTag.where().eq(Tag.TAG_ID, ID);
		PreparedQuery<Tag> preparedQueryTag = queryBuilderTag.prepare();
		Tagresult = tagLocationAll.query(preparedQueryTag);

		return Tagresult;

	}

	/**
	 * Gets the room by id.
	 *
	 * @param ID id of the object
	 * @return Roomobject
	 * @throws SQLException the SQL exception
	 */
	public List<Room> getRoomById(String ID) throws SQLException {

		if (this.roomAll == null) {
			this.roomAll = this.getRoomDataDao();
		}
		QueryBuilder<Room, Integer> queryBuilderRoom = null;

		queryBuilderRoom = roomAll.queryBuilder();
		queryBuilderRoom.where().eq(Room.ROOM_ID, ID);
		PreparedQuery<Room> preparedQueryRoom = queryBuilderRoom.prepare();
		roomResult = roomAll.query(preparedQueryRoom);

		return roomResult;
	}

	/**
	 * Gets the roomtype by id.
	 *
	 * @param ID id of the object
	 * @return Roomtypeobject 
	 * @throws SQLException the SQL exception
	 */
	public List<Roomtype> getRoomtypeById(String ID) throws SQLException {
		Dao<Roomtype, Integer> roomTypeAll = this.getRoomtypeDataDao();

		QueryBuilder<Roomtype, Integer> queryBuilderRoomType = null;

		queryBuilderRoomType = roomTypeAll.queryBuilder();
		queryBuilderRoomType.where().eq(Roomtype.ROOMTYPE_ID, ID);
		PreparedQuery<Roomtype> preparedQueryRoomType = queryBuilderRoomType.prepare();
		List<Roomtype> roomTypeResult = roomTypeAll.query(preparedQueryRoomType);

		return roomTypeResult;
	}

	/**
	 * Update history items name.
	 *
	 * @param ID id of the Object which will be updated
	 * @param newName the new name
	 */
	public void updateHistoryItemName(Integer ID, String newName) {

		try {
			Dao<HistoryItem, Integer> hisDao = this.getHistoryItemDataDao();
			UpdateBuilder<HistoryItem, Integer> updateBuilder = hisDao.updateBuilder();
			updateBuilder.where().eq(HistoryItem.ID, ID);
			updateBuilder.updateColumnValue(HistoryItem.NAME, newName);
			updateBuilder.update();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Gets all the history items.
	 * 
	 * @return  history items
	 */
	public List<HistoryItem> getHistoryItems() {

		List<HistoryItem> result = new ArrayList<HistoryItem>();
		try {
			PreparedQuery<HistoryItem> preparedQuery = this.getHistoryItemDataDao().queryBuilder().prepare();
			result = this.getHistoryItemDataDao().query(preparedQuery);

		} catch (Exception e) {
			e.printStackTrace();

		}

		return result;
	}
	
	/**
	 * Deletes an  history item by given id.
	 *
	 * @param id id of the Object which will be deleted
	 * @throws SQLException the SQL exception
	 */
	public void deleteHistoryItemById(Integer id) throws SQLException{
		
		Dao<HistoryItem, Integer> historyItemAll = this.getHistoryItemDataDao();

		QueryBuilder<HistoryItem, Integer> queryBuilderRoomType = null;

		queryBuilderRoomType = historyItemAll.queryBuilder();
		queryBuilderRoomType.where().eq(HistoryItem.ID, id);
		PreparedQuery<HistoryItem> preparedQueryRoomType = queryBuilderRoomType.prepare();
		List<HistoryItem> historyItemResult = historyItemAll.query(preparedQueryRoomType);
		historyItemAll.delete(historyItemResult);		
		
		
	}
}
