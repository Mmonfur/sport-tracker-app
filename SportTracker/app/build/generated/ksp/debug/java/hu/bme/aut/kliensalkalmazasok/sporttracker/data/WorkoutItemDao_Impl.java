package hu.bme.aut.kliensalkalmazasok.sporttracker.data;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.IllegalStateException;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class WorkoutItemDao_Impl implements WorkoutItemDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<WorkoutItem> __insertionAdapterOfWorkoutItem;

  private final EntityDeletionOrUpdateAdapter<WorkoutItem> __deletionAdapterOfWorkoutItem;

  private final EntityDeletionOrUpdateAdapter<WorkoutItem> __updateAdapterOfWorkoutItem;

  public WorkoutItemDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWorkoutItem = new EntityInsertionAdapter<WorkoutItem>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `workoutitem` (`id`,`date`,`description`,`type`,`duration`,`is_completed`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WorkoutItem entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindLong(1, entity.getId());
        }
        statement.bindLong(2, entity.getDate());
        statement.bindString(3, entity.getDescription());
        final int _tmp = WorkoutItem.Type.toInt(entity.getType());
        statement.bindLong(4, _tmp);
        statement.bindLong(5, entity.getDuration());
        final int _tmp_1 = entity.isCompleted() ? 1 : 0;
        statement.bindLong(6, _tmp_1);
      }
    };
    this.__deletionAdapterOfWorkoutItem = new EntityDeletionOrUpdateAdapter<WorkoutItem>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `workoutitem` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WorkoutItem entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindLong(1, entity.getId());
        }
      }
    };
    this.__updateAdapterOfWorkoutItem = new EntityDeletionOrUpdateAdapter<WorkoutItem>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `workoutitem` SET `id` = ?,`date` = ?,`description` = ?,`type` = ?,`duration` = ?,`is_completed` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WorkoutItem entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindLong(1, entity.getId());
        }
        statement.bindLong(2, entity.getDate());
        statement.bindString(3, entity.getDescription());
        final int _tmp = WorkoutItem.Type.toInt(entity.getType());
        statement.bindLong(4, _tmp);
        statement.bindLong(5, entity.getDuration());
        final int _tmp_1 = entity.isCompleted() ? 1 : 0;
        statement.bindLong(6, _tmp_1);
        if (entity.getId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getId());
        }
      }
    };
  }

  @Override
  public long insert(final WorkoutItem workoutItems) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfWorkoutItem.insertAndReturnId(workoutItems);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteItem(final WorkoutItem workoutItem) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfWorkoutItem.handle(workoutItem);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final WorkoutItem workoutItem) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfWorkoutItem.handle(workoutItem);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<WorkoutItem> getAll() {
    final String _sql = "SELECT * FROM workoutitem";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
      final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_completed");
      final List<WorkoutItem> _result = new ArrayList<WorkoutItem>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final WorkoutItem _item;
        final Long _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getLong(_cursorIndexOfId);
        }
        final long _tmpDate;
        _tmpDate = _cursor.getLong(_cursorIndexOfDate);
        final String _tmpDescription;
        _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        final WorkoutItem.Type _tmpType;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfType);
        final WorkoutItem.Type _tmp_1 = WorkoutItem.Type.getByOrdinal(_tmp);
        if (_tmp_1 == null) {
          throw new IllegalStateException("Expected NON-NULL 'hu.bme.aut.kliensalkalmazasok.sporttracker.data.WorkoutItem.Type', but it was NULL.");
        } else {
          _tmpType = _tmp_1;
        }
        final int _tmpDuration;
        _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
        final boolean _tmpIsCompleted;
        final int _tmp_2;
        _tmp_2 = _cursor.getInt(_cursorIndexOfIsCompleted);
        _tmpIsCompleted = _tmp_2 != 0;
        _item = new WorkoutItem(_tmpId,_tmpDate,_tmpDescription,_tmpType,_tmpDuration,_tmpIsCompleted);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<WorkoutItem> getItemByIsCompleted(final boolean isCompleted) {
    final String _sql = "SELECT * FROM workoutitem WHERE is_completed = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final int _tmp = isCompleted ? 1 : 0;
    _statement.bindLong(_argIndex, _tmp);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
      final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_completed");
      final List<WorkoutItem> _result = new ArrayList<WorkoutItem>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final WorkoutItem _item;
        final Long _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getLong(_cursorIndexOfId);
        }
        final long _tmpDate;
        _tmpDate = _cursor.getLong(_cursorIndexOfDate);
        final String _tmpDescription;
        _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        final WorkoutItem.Type _tmpType;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfType);
        final WorkoutItem.Type _tmp_2 = WorkoutItem.Type.getByOrdinal(_tmp_1);
        if (_tmp_2 == null) {
          throw new IllegalStateException("Expected NON-NULL 'hu.bme.aut.kliensalkalmazasok.sporttracker.data.WorkoutItem.Type', but it was NULL.");
        } else {
          _tmpType = _tmp_2;
        }
        final int _tmpDuration;
        _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
        final boolean _tmpIsCompleted;
        final int _tmp_3;
        _tmp_3 = _cursor.getInt(_cursorIndexOfIsCompleted);
        _tmpIsCompleted = _tmp_3 != 0;
        _item = new WorkoutItem(_tmpId,_tmpDate,_tmpDescription,_tmpType,_tmpDuration,_tmpIsCompleted);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<WorkoutItem> getItemsBetween(final long from, final long to) {
    final String _sql = "SELECT * FROM workoutitem WHERE date BETWEEN ? AND ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, from);
    _argIndex = 2;
    _statement.bindLong(_argIndex, to);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
      final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_completed");
      final List<WorkoutItem> _result = new ArrayList<WorkoutItem>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final WorkoutItem _item;
        final Long _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getLong(_cursorIndexOfId);
        }
        final long _tmpDate;
        _tmpDate = _cursor.getLong(_cursorIndexOfDate);
        final String _tmpDescription;
        _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        final WorkoutItem.Type _tmpType;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfType);
        final WorkoutItem.Type _tmp_1 = WorkoutItem.Type.getByOrdinal(_tmp);
        if (_tmp_1 == null) {
          throw new IllegalStateException("Expected NON-NULL 'hu.bme.aut.kliensalkalmazasok.sporttracker.data.WorkoutItem.Type', but it was NULL.");
        } else {
          _tmpType = _tmp_1;
        }
        final int _tmpDuration;
        _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
        final boolean _tmpIsCompleted;
        final int _tmp_2;
        _tmp_2 = _cursor.getInt(_cursorIndexOfIsCompleted);
        _tmpIsCompleted = _tmp_2 != 0;
        _item = new WorkoutItem(_tmpId,_tmpDate,_tmpDescription,_tmpType,_tmpDuration,_tmpIsCompleted);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
