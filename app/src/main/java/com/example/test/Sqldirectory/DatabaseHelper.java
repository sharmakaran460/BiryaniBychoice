package com.example.test.Sqldirectory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Deepak Nishad on 26-Jul-16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "BIRYANI_DB";
    private static final int DATABASE_VERSION = 1;
    private static DatabaseHelper dbHelper = null;
    private static SQLiteDatabase db = null;

    private static final String RC_DETAILS_TABLE = "RC_DETAILS_TABLE";

    private static final String ID = "ID";
    private static final String product_id = "Product_ID";
    private static final String product_name = "PRODUCT_NAME";
    private static final String product_dec = "PRODUCT_DEC";
    private static final String product_Food_image = "PRODUCT_FOOD_IMAGE";
    private static final String product_PRICE = "PRODUCT_PRICE";
    private static final String product_Quantity = "PRODUCT_QUANTITY";



    private static final String RC_DETAILS_TABLE_QUERY = "create table " + RC_DETAILS_TABLE + " ( " + ID + " integer primary key autoincrement, "
            + product_id + " text NOT NULL unique, " + product_name + " text, " + product_dec + " text, "+ product_Food_image+ " text, "+product_PRICE+ " text, "+product_Quantity+ " text);";




    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        dbHelper = this;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(RC_DETAILS_TABLE_QUERY);
//        database.execSQL(DL_DETAILS_TABLE_QUERY);
//        database.execSQL(RC_SHARING_TABLE_QUERY);
//        database.execSQL(CITIZEN_OFFENCE_LIST_QUERY);
//        database.execSQL(STATE_WISE_SERVICE_TABLE_QUERRY);
//        database.execSQL(GENERAL_NOTIFICATIONS_TABLE_QUERY);
//        database.execSQL(RECENT_SEARCH_TABLE_QUERY);
//        database.execSQL(MOCK_DATA_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
//        database.execSQL("DROP TABLE IF EXISTS " + RC_DETAILS_TABLE);
//        database.execSQL("DROP TABLE IF EXISTS " + RC_SHARING_TABLE);
//        database.execSQL("DROP TABLE IF EXISTS " + DL_DETAILS_TABLE);
//        database.execSQL("DROP TABLE IF EXISTS " + CITIZEN_OFFENCE_LIST_TABLE);
//        database.execSQL("DROP TABLE IF EXISTS " + STATE_WISE_SERVICE_TABLE);
//        database.execSQL("DROP TABLE IF EXISTS " + GENERAL_NOTIFICATIONS_TABLE);
//        database.execSQL("DROP TABLE IF EXISTS " + RECENT_SEARCH_TABLE);
//        database.execSQL("DROP TABLE IF EXISTS " + MOCK_DATA_TABLE_QUERY);


        database.execSQL("DROP TABLE IF EXISTS " + RC_DETAILS_TABLE);
//        database.execSQL("DROP TABLE IF EXISTS " + RC_SHARING_TABLE);
//        database.execSQL("DROP TABLE IF EXISTS " + DL_DETAILS_TABLE);
//        database.execSQL("DROP TABLE IF EXISTS " + CITIZEN_OFFENCE_LIST_TABLE);
//        database.execSQL("DROP TABLE IF EXISTS " + STATE_WISE_SERVICE_TABLE);
//        database.execSQL("DROP TABLE IF EXISTS " + GENERAL_NOTIFICATIONS_TABLE);
//        database.execSQL("DROP TABLE IF EXISTS " + RECENT_SEARCH_TABLE);
//        database.execSQL("DROP TABLE IF EXISTS " + MOCK_DATA_TABLE);
        onCreate(database);
    }





//insert the cart data into the app--
    public long save_cart_value(String  product_id_txt, String product_name_txt,String product_dec_txt,String product_Food_image_txt,String product_PRICE_txt,String product_Quantity_txt) {
        //Log.v("DB_RC_DETAILS", rcID);
        SQLiteDatabase db = null;
        long count = -1;
        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(product_id, product_id_txt);
            values.put(product_name,product_name_txt);
            values.put(product_dec, product_dec_txt);
            values.put(product_Food_image, product_Food_image_txt);
            values.put(product_PRICE, product_PRICE_txt);
            values.put(product_Quantity, product_Quantity_txt);
            Cursor cursor = null;
            try {
                db = this.getReadableDatabase();
                String query = "select *" + " from " + RC_DETAILS_TABLE + " WHERE " + product_id + " =" + "'" + product_id_txt + "'";
                cursor = db.rawQuery(query, null);
                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        count = db.update(RC_DETAILS_TABLE, values, product_id + "=?", new String[]{String.valueOf(product_id_txt)});
                        return count;
                    } else {
                        count = db.insert(RC_DETAILS_TABLE, null, values);
                        //Log.v("RC_SAVE_STATUS", "Count : " + count);
                        return count;
                    }
                } else {
                    count = db.insert(RC_DETAILS_TABLE, null, values);
                    //Log.v("RC_SAVE_STATUS", "Count : " + count);
                    return count;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //  count = db.insert(RC_DETAILS_TABLE, null, values);
            //Log.v("RC_SAVE_STATUS", "Count : " + count);
            return count;
        } catch (SQLiteConstraintException e) {
            e.printStackTrace();
            return count;
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return count;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }



    //get the cart details-->

    public String get_the_total_amount()
    {
        SQLiteDatabase db = null;
        String amount_total = null;
        int total_amount = 0;

        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            String query = "select *" + " from " + RC_DETAILS_TABLE ;
            cursor = db.rawQuery(query, null);
            if (cursor != null) {

                if (cursor.moveToFirst())
                {
                    do
                        {

                     String amount =   cursor.getString(5);
                     String quantity =  cursor.getString(6);

                            total_amount = total_amount+(Integer.parseInt(amount) * (Integer.parseInt(quantity)));

                     Log.e("Amount"," "+amount+" "+quantity);

                            amount_total=amount;
                    } while (cursor.moveToNext());
                }
            }
            return Integer.toString(total_amount);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            if (cursor != null && !cursor.isClosed())
            {
                cursor.close();
            }
            if (db != null)
            {
                db.close();
            }
        }
    }



    public String get_the_total_quantity()
    {
        SQLiteDatabase db = null;
        String quantity_total = null;
        int total_amount = 0;

        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            String query = "select *" + " from " + RC_DETAILS_TABLE ;
            cursor = db.rawQuery(query, null);
            if (cursor != null) {

                if (cursor.moveToFirst())
                {
                    do
                    {

                        String amount =   cursor.getString(5);
                        String quantity =  cursor.getString(6);

                        total_amount = total_amount+Integer.parseInt(quantity);

                        Log.e("Amount"," "+amount+" "+quantity);


                    } while (cursor.moveToNext());
                }
            }
            return Integer.toString(total_amount);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            if (cursor != null && !cursor.isClosed())
            {
                cursor.close();
            }
            if (db != null)
            {
                db.close();
            }
        }
    }







    public String get_the_total_quantity_by_id(String product_id1)
    {
        SQLiteDatabase db = null;
        String quantity_total = null;
        int total_amount = 0;

        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            String query = "select *" + " from " + RC_DETAILS_TABLE + " WHERE " + product_id + " =" + "'" + product_id1 + "'";
            cursor = db.rawQuery(query, null);
            if (cursor != null) {

                if (cursor.moveToFirst())
                {
                    do
                    {

                        String amount =   cursor.getString(5);
                        String quantity =  cursor.getString(6);

                        total_amount = total_amount+Integer.parseInt(quantity);

                        Log.e("Amount-----"," "+" "+quantity);


                    } while (cursor.moveToNext());
                }
            }
            return Integer.toString(total_amount);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            if (cursor != null && !cursor.isClosed())
            {
                cursor.close();
            }
            if (db != null)
            {
                db.close();
            }
        }
    }










}
