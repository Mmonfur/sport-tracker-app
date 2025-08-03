package hu.bme.aut.kliensalkalmazasok.sporttracker.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import hu.bme.aut.kliensalkalmazasok.sporttracker.R;
import hu.bme.aut.kliensalkalmazasok.sporttracker.data.WorkoutItem;
import java.io.Serializable;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class WorkoutListFragmentDirections {
  private WorkoutListFragmentDirections() {
  }

  @CheckResult
  @NonNull
  public static NavDirections actionWorkoutListFragmentToCreateNewWorkoutItemFragment() {
    return new ActionOnlyNavDirections(R.id.action_workoutListFragment_to_createNewWorkoutItemFragment);
  }

  @CheckResult
  @NonNull
  public static NavDirections actionWorkoutListFragmentToChartFragment() {
    return new ActionOnlyNavDirections(R.id.action_workoutListFragment_to_chartFragment);
  }

  @CheckResult
  @NonNull
  public static NavDirections actionWorkoutListFragmentToWeeklyStatsFragment() {
    return new ActionOnlyNavDirections(R.id.action_workoutListFragment_to_weeklyStatsFragment);
  }

  @CheckResult
  @NonNull
  public static ActionListToEdit actionListToEdit(@NonNull WorkoutItem workoutItem) {
    return new ActionListToEdit(workoutItem);
  }

  public static class ActionListToEdit implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionListToEdit(@NonNull WorkoutItem workoutItem) {
      if (workoutItem == null) {
        throw new IllegalArgumentException("Argument \"workout_item\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("workout_item", workoutItem);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionListToEdit setWorkoutItem(@NonNull WorkoutItem workoutItem) {
      if (workoutItem == null) {
        throw new IllegalArgumentException("Argument \"workout_item\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("workout_item", workoutItem);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("workout_item")) {
        WorkoutItem workoutItem = (WorkoutItem) arguments.get("workout_item");
        if (Parcelable.class.isAssignableFrom(WorkoutItem.class) || workoutItem == null) {
          __result.putParcelable("workout_item", Parcelable.class.cast(workoutItem));
        } else if (Serializable.class.isAssignableFrom(WorkoutItem.class)) {
          __result.putSerializable("workout_item", Serializable.class.cast(workoutItem));
        } else {
          throw new UnsupportedOperationException(WorkoutItem.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
        }
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_list_to_edit;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public WorkoutItem getWorkoutItem() {
      return (WorkoutItem) arguments.get("workout_item");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionListToEdit that = (ActionListToEdit) object;
      if (arguments.containsKey("workout_item") != that.arguments.containsKey("workout_item")) {
        return false;
      }
      if (getWorkoutItem() != null ? !getWorkoutItem().equals(that.getWorkoutItem()) : that.getWorkoutItem() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getWorkoutItem() != null ? getWorkoutItem().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionListToEdit(actionId=" + getActionId() + "){"
          + "workoutItem=" + getWorkoutItem()
          + "}";
    }
  }
}
