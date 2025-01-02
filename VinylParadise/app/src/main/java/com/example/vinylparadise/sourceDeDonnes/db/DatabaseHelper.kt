import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "recherches.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_RECHERCHES = "recherche"
        const val COLUMN_ID = "id"
        const val COLUMN_TERM = "term"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_RECHERCHES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TERM TEXT NOT NULL
            )
        """.trimIndent()
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_RECHERCHES")
        onCreate(db)
    }

    fun AjouterRecherche(term: String) {
        val db = writableDatabase
        val query = "INSERT INTO $TABLE_RECHERCHES ($COLUMN_TERM) VALUES (?)"
        db.execSQL(query, arrayOf(term))
        db.close()
    }

    fun obtenirToutesLesRecherches(): List<String> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT $COLUMN_TERM FROM $TABLE_RECHERCHES", null)
        val terms = mutableListOf<String>()

        if (cursor.moveToFirst()) {
            do {
                terms.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TERM)))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return terms
    }
    fun effacerHistorique() {
        val db = writableDatabase
        db.execSQL("DELETE FROM $TABLE_RECHERCHES")
        db.close()
    }


}
