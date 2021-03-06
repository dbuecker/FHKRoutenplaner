package de.damianbuecker.fhkroutenplaner.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;

import de.damianbuecker.fhkroutenplaner.activity.R;
import de.damianbuecker.fhkroutenplaner.databaseaccess.DatabaseHelper;
import de.damianbuecker.fhkroutenplaner.databaseaccess.Docent;
import de.damianbuecker.fhkroutenplaner.databaseaccess.Edges;
import de.damianbuecker.fhkroutenplaner.databaseaccess.JSONParser;
import de.damianbuecker.fhkroutenplaner.databaseaccess.Room;
import de.damianbuecker.fhkroutenplaner.databaseaccess.Roomtype;
import de.damianbuecker.fhkroutenplaner.databaseaccess.Tag;

// TODO: Auto-generated Javadoc
/**
 * The Class StartupContoller.
 */
public class StartupContoller extends Controller {

	/** The conn manager. */
	private ConnectivityManager connManager;

	/** The database helper. */
	private DatabaseHelper databaseHelper;

	/** The m wifi. */
	private NetworkInfo mWifi;

	/** The Constant tables. */
	private static final String[] tables = { "edges", "tag", "room", "docent", "roomtype" };

	/** The j parser. */
	private JSONParser jParser = new JSONParser();

	/** The external data. */
	private JSONArray externalData;

	/** The params. */
	private List<NameValuePair> params;

	/** The version. */
	private String version;

	/** The m shared preferences controller. */
	private SharedPreferencesController mSharedPreferencesController;

	/** The url_get database. */
	private String url_getDatabase = "http://fhkrp.in24.de/BA/index.php";

	/** The url_get version. */
	private String url_getVersion = "http://fhkrp.in24.de/BA/index2.php";

	/** The Constant TAG_SUCCESS. */
	private static final String TAG_SUCCESS = "success";

	/** The edges dao. */
	private Dao<Edges, Integer> edgesDao;

	/** The tag dao. */
	private Dao<Tag, Integer> tagDao;

	/** The room dao. */
	private Dao<Room, Integer> roomDao;

	/** The docent dao. */
	private Dao<Docent, Integer> docentDao;

	/** The roomtype dao. */
	private Dao<Roomtype, Integer> roomtypeDao;

	/** The Constant TAG_DOCENTID. */
	protected static final String TAG_DOCENTID = "docentID";

	/** The Constant TAG_ROOMID. */
	protected static final String TAG_ROOMID = "roomID";

	/** The Constant TAG_FLOOR. */
	protected static final String TAG_FLOOR = "floor";

	/** The Constant TAG_ROOMTYPEID. */
	protected static final String TAG_ROOMTYPEID = "roomtypeID";

	/** The Constant TAG_DESCRIPTION. */
	protected static final String TAG_DESCRIPTION = "description";

	/** The Constant TAG_TAGID. */
	protected static final String TAG_TAGID = "tagID";

	/** The Constant TAG_XOS. */
	protected static final String TAG_XOS = "x_pos";

	/** The Constant TAG_YPOS. */
	protected static final String TAG_YPOS = "y_pos";

	/** The Constant TAG_NAME. */
	protected static final String TAG_NAME = "name";

	/** The Constant TAG_LASTNAME. */
	protected static final String TAG_LASTNAME = "lastname";

	/** The Constant TAG_EDGESID. */
	protected static final String TAG_EDGESID = "edgesID";

	/** The Constant TAG_SOURCE. */
	protected static final String TAG_SOURCE = "source";

	/** The Constant TAG_DESTINATION. */
	protected static final String TAG_DESTINATION = "destination";

	/** The Constant TAG_COST. */
	protected static final String TAG_COST = "cost";

	/** The Constant TAG_VERSION. */
	protected static final String TAG_VERSION = "version";

	/** The Constant ROOM_TXT. */
	private static final String ROOM_TXT = "/sdcard/FMS/room.txt";

	/** The Constant TAG_TXT. */
	private static final String TAG_TXT = "/sdcard/FMS/tag.txt";

	/** The Constant ROOMTYPE_TXT. */
	private static final String ROOMTYPE_TXT = "/sdcard/FMS/roomtype.txt";

	/** The Constant DOCENT_TXT. */
	private static final String DOCENT_TXT = "/sdcard/FMS/docent.txt";

	/** The Constant EDGES_TXT. */
	private static final String EDGES_TXT = "/sdcard/FMS/edges.txt";

	/** The external database version. */
	private Integer externalDatabaseVersion;

	/**
	 * Gets the external database version.
	 *
	 * @return the external database version
	 */
	public Integer getExternalDatabaseVersion() {
		return externalDatabaseVersion;
	}

	/**
	 * Sets the external database version.
	 *
	 * @param externalDatabaseVersion the new external database version
	 */
	public void setExternalDatabaseVersion(Integer externalDatabaseVersion) {
		this.externalDatabaseVersion = externalDatabaseVersion;
	}

	/**
	 * Instantiates a new startup contoller.
	 * 
	 * @param context
	 *            the context
	 */
	public StartupContoller(Context context) {
		super(context);
	}

	/**
	 * Checks if is wifi connected.
	 * 
	 * @return true, if is wifi connected
	 */
	@SuppressWarnings("static-access")
	public boolean isWifiConnected() {

		this.connManager = (ConnectivityManager) this.getContext().getSystemService(this.getContext().CONNECTIVITY_SERVICE);
		this.mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		return (mWifi.isConnected()) ? true : false;
	}

	/**
	 * Gets the external database.
	 * 
	 * @return  external database
	 */
	public void getExternalDatabase() {

		if (this.databaseHelper == null) {
			databaseHelper = this.getDatabaseHelper(this.getContext());
		}

		databaseHelper.deleteCompleteDatabase();

		for (String tblnames : tables) {

			params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("tablename", tblnames));
			try {
				JSONObject json = jParser.makeHttpRequest(url_getDatabase, "GET", params);

				try {
					int success = json.getInt(TAG_SUCCESS);

					if (success == 1) {
						externalData = json.getJSONArray(tblnames);

						for (int i = 0; i < externalData.length(); i++) {
							JSONObject c = externalData.getJSONObject(i);

							if (tblnames.equals("room")) {
								try {
									if (roomDao == null) {
										roomDao = databaseHelper.getRoomDataDao();
									}

									Room room = new Room();
									room.setRoom_id(Integer.parseInt(c.getString(TAG_ROOMID)));
									room.setFloor(Integer.parseInt(c.getString(TAG_FLOOR)));
									room.setRoomtype_ID(Integer.parseInt(c.getString(TAG_ROOMTYPEID)));
									room.setDocent_ID(Integer.parseInt(c.getString(TAG_DOCENTID)));
									room.setDescription(c.getString(TAG_DESCRIPTION));

									roomDao.create(room);

								} catch (Exception e) {
									e.printStackTrace();
								}

							} else if (tblnames.equals("tag")) {

								try {
									if (tagDao == null) {
										tagDao = databaseHelper.getTagDataDao();
									}

									Tag tag = new Tag();

									tag.setTag_id(Integer.parseInt(c.getString(TAG_TAGID)));
									tag.setRoom_ID(Integer.parseInt(c.getString(TAG_ROOMID)));
									tag.setX_pos(Double.parseDouble(c.getString(TAG_XOS)));
									tag.setY_pos(Double.parseDouble(c.getString(TAG_YPOS)));
									tag.setDescription(c.getString(TAG_DESCRIPTION));
									tag.setFloor(Integer.parseInt(c.getString(TAG_FLOOR)));
									tagDao.create(tag);

								} catch (Exception e) {
									e.printStackTrace();
								}

							} else if (tblnames.equals("roomtype")) {

								try {
									if (roomtypeDao == null) {
										roomtypeDao = databaseHelper.getRoomtypeDataDao();
									}

									Roomtype roomtype = new Roomtype();

									roomtype.setRoomtype_id(Integer.parseInt(c.getString(TAG_ROOMTYPEID)));
									roomtype.setDescription(c.getString(TAG_DESCRIPTION));

									roomtypeDao.create(roomtype);

								} catch (Exception e) {
									e.printStackTrace();
								}

							} else if (tblnames.equals("docent")) {
								try {
									if (docentDao == null) {

										docentDao = databaseHelper.getDocentDataDao();
									}

									Docent docent = new Docent();

									docent.setDozent_id(Integer.parseInt(c.getString(TAG_DOCENTID)));
									docent.setD_name(c.getString(TAG_NAME));
									docent.setD_lastname(c.getString(TAG_LASTNAME));

									docentDao.create(docent);

								} catch (Exception e) {
									e.printStackTrace();
								}

							} else if (tblnames.equals("edges")) {

								try {
									if (edgesDao == null) {
										edgesDao = databaseHelper.getEdgesDataDao();
									}

									Edges edge = new Edges();

									edge.setKante_id(Integer.parseInt(c.getString(TAG_EDGESID)));
									edge.setSource(Integer.parseInt(c.getString(TAG_SOURCE)));
									edge.setDestination(Integer.parseInt(c.getString(TAG_DESTINATION)));
									edge.setCost(Integer.parseInt(c.getString(TAG_COST)));

									edgesDao.create(edge);

								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (Exception ex) {
			}
		}
	}

	/**
	 * Gets the external database version.
	 * 
	 * @return  database version
	 */
	public void getDatabaseVersion() {
		params = new ArrayList<NameValuePair>();

		try {
			JSONObject json = jParser.makeHttpRequest(url_getVersion, "GET", params);

			int success = json.getInt(TAG_SUCCESS);

			if (success == 1) {
				externalData = json.getJSONArray("version");

				for (int i = 0; i < externalData.length(); i++) {
					JSONObject c = externalData.getJSONObject(i);

					version = c.getString(TAG_VERSION);
				}

				setExternalDatabaseVersion(Integer.parseInt(version));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Check for update.
	 * 
	 * @return the integer
	 */
	public Integer checkForUpdate() {

		this.mSharedPreferencesController = new SharedPreferencesController(this.getContext());
		String externalVersion = String.valueOf(this.getExternalDatabaseVersion());
		Integer internalVersion = this.mSharedPreferencesController.getInteger(SHARED_PREFERENCE_DATABASE_VERSION);

		if (externalVersion != "0" || internalVersion != 0) {
			if (Integer.parseInt(externalVersion) > internalVersion) {

				return 2;
			}
		}
		return -1;

	}
	
	/**
	 * Delete old images.
	 *
	 * @return true, if successful
	 */
	public boolean deleteOldImages() {
		
		StringBuffer path = new StringBuffer();
		boolean success = true;
		
		path.append(Environment.getExternalStorageDirectory()).append(this.getContext().getString(R.string.workingDirectory));
		this.logMessage("INFO", path.toString());
		
		File directory = new File(path.toString());
		File[] files = directory.listFiles();
		
		for(File file : files) {
			if(!success) {
				return success;
			} else {
				if(file.getName().contains(this.getContext().getString(R.string.imageFilename))) {
					this.logMessage("INFO", file.getName());
					success = file.delete();
				}
			}
		}
		
		return success;
	}
}
