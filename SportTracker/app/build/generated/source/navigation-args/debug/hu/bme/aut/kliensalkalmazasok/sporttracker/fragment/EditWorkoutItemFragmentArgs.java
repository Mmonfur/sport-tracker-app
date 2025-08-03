package hu.bme.aut.kliensalkalmazasok.sporttracker.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;
import hu.bme.aut.kliensalkalmazasok.sporttracker.data.WorkoutItem;
import java.io.Serializable;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class EditWorkoutItemFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private EditWorkoutItemFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private EditWorkoutItemFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings({
      "unchecked",
      "deprecation"
  })
  public static EditWorkoutItemFragmentArgs fromBundle(@NonNull Bundle bundle) {
    EditWorkoutItemFragmentArgs __result = new EditWorkoutItemFragmentArgs();
    bundle.setClassLoader(EditWorkoutItemFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("workout_item")) {
      WorkoutItem workoutItem;
      if (Parcelable.class.isAssignableFrom(WorkoutItem.class) || Serializable.class.isAssignableFrom(WorkoutItem.class)) {
        workoutItem = (WorkoutItem) bundle.get("workout_item");
      } else {
        throw new UnsupportedOperationException(WorkoutItem.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
      if (workoutItem == null) {
        throw new IllegalArgumentException("Argument \"workout_item\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("workout_item", workoutItem);
    } else {
      throw new IllegalArgumentException("Required argument \"workout_item\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static EditWorkoutItemFragmentArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    EditWorkoutItemFragmentArgs __result = new EditWorkoutItemFragmentArgs();
    if (savedStateHandle.contains("workout_item")) {
      WorkoutItem workoutItem;
      workoutItem = savedStateHandle.get("workout_item");
      if (workoutItem == null) {
        throw new IllegalArgumentException("Argument \"workout_item\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("workout_item", workoutItem);
    } else {
      throw new IllegalArgumentException("Required argument \"workout_item\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public WorkoutItem getWorkoutItem() {
    return (WorkoutItem) arguments.get("workout_item");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
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

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("workout_item")) {
      WorkoutItem workoutItem = (WorkoutItem) arguments.get("workout_item");
      if (Parcelable.class.isAssignableFrom(WorkoutItem.class) || workoutItem == null) {
        __result.set("workout_item", Parcelable.class.cast(workoutItem));
      } else if (Serializable.class.isAssignableFrom(WorkoutItem.class)) {
        __result.set("workout_item", Serializable.class.cast(workoutItem));
      } else {
        throw new UnsupportedOperationException(WorkoutItem.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
    }
    return __result;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
        return true;
    }
    if (object == null || getClass() != object.getClass()) {
        return false;
    }
    EditWorkoutItemFragmentArgs that = (EditWorkoutItemFragmentArgs) object;
    if (arguments.containsKey("workout_item") != that.arguments.containsKey("workout_item")) {
      return false;
    }
    if (getWorkoutItem() != null ? !getWorkoutItem().equals(that.getWorkoutItem()) : that.getWorkoutItem() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getWorkoutItem() != null ? getWorkoutItem().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "EditWorkoutItemFragmentArgs{"
        + "workoutItem=" + getWorkoutItem()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull EditWorkoutItemFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(@NonNull WorkoutItem workoutItem) {
      if (workoutItem == null) {
        throw new IllegalArgumentException("Argument \"workout_item\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("workout_item", workoutItem);
    }

    @NonNull
    public EditWorkoutItemFragmentArgs build() {
      EditWorkoutItemFragmentArgs result = new EditWorkoutItemFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setWorkoutItem(@NonNull WorkoutItem workoutItem) {
      if (workoutItem == null) {
        throw new IllegalArgumentException("Argument \"workout_item\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("workout_item", workoutItem);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public WorkoutItem getWorkoutItem() {
      return (WorkoutItem) arguments.get("workout_item");
    }
  }
}
