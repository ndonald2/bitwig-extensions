package com.bitwig.extensions.framework2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

import com.bitwig.extension.controller.api.AbsoluteHardwarControlBindable;
import com.bitwig.extension.controller.api.AbsoluteHardwareControl;
import com.bitwig.extension.controller.api.BoolHardwareOutputValue;
import com.bitwig.extension.controller.api.BooleanValue;
import com.bitwig.extension.controller.api.HardwareAction;
import com.bitwig.extension.controller.api.HardwareActionBindable;
import com.bitwig.extension.controller.api.HardwareButton;
import com.bitwig.extension.controller.api.HardwareControl;
import com.bitwig.extension.controller.api.HardwareLight;
import com.bitwig.extension.controller.api.RelativeHardwarControlBindable;
import com.bitwig.extension.controller.api.RelativeHardwareControl;
import com.bitwig.extension.controller.api.SettableRangedValue;

/**
 * A layer defines a number of bindings between a source object and a target that should be active when the
 * layer is active. A layer on tap will override bindings from a lower layer.
 */
public class Layer
{
   Layer(final Layers layers, final String name)
   {
      super();
      mLayers = layers;
      mName = name;
   }

   public String getName()
   {
      return mName;
   }

   @SuppressWarnings("rawtypes")
   public void addBinding(final Binding binding)
   {
      assert !mBindings.contains(binding);
      assert !isActive();

      mBindings.add(binding);

      binding.setLayer(this);
   }

   public AbsoluteHardwareControlBinding bind(
      final AbsoluteHardwareControl source,
      final AbsoluteHardwarControlBindable target)
   {
      final AbsoluteHardwareControlBinding binding = new AbsoluteHardwareControlBinding(source, target);

      addBinding(binding);

      return binding;
   }

   public RelativeHardwareControlToRangedValueBinding bind(
      final RelativeHardwareControl source,
      final SettableRangedValue target)
   {
      final RelativeHardwareControlToRangedValueBinding binding = new RelativeHardwareControlToRangedValueBinding(
         source, target);

      addBinding(binding);

      return binding;
   }

   public RelativeHardwareControlBinding bind(
      final RelativeHardwareControl source,
      final RelativeHardwarControlBindable target)
   {
      final RelativeHardwareControlBinding binding = new RelativeHardwareControlBinding(source, target);

      addBinding(binding);

      return binding;
   }

   public HarwareActionBinding bind(final HardwareAction source, final HardwareActionBindable target)
   {
      final HarwareActionBinding binding = new HarwareActionBinding(source, target);

      addBinding(binding);

      return binding;
   }

   public HardwareActionRunnableBinding bind(final HardwareAction source, final Runnable target)
   {
      final HardwareActionRunnableBinding binding = new HardwareActionRunnableBinding(source, target);

      addBinding(binding);

      return binding;
   }

   public HardwareActionRunnableBinding bind(final HardwareButton source, final Runnable target)
   {
      return bind(source.pressedAction(), target);
   }

   public HarwareActionBinding bind(final HardwareButton button, final HardwareActionBindable target)
   {
      return bind(button.pressedAction(), target);
   }

   public BooleanSupplierOutputValueBinding bind(
      final BooleanSupplier source,
      final BoolHardwareOutputValue target)
   {
      final BooleanSupplierOutputValueBinding binding = new BooleanSupplierOutputValueBinding(source, target);

      addBinding(binding);

      return binding;
   }

   public BooleanSupplierOutputValueBinding bind(
      final BooleanSupplier source,
      final HardwareLight target)
   {
      return bind(source, target.isOn());
   }

   public BooleanSupplierOutputValueBinding bind(
      final BooleanSupplier source,
      final HardwareControl target)
   {
      return bind(source, target.backgroundLight());
   }

   public BooleanValueOutputValueBinding bind(final BooleanValue source, final BoolHardwareOutputValue target)
   {
      final BooleanValueOutputValueBinding binding = new BooleanValueOutputValueBinding(source, target);

      addBinding(binding);

      return binding;
   }

   public BooleanValueOutputValueBinding bind(final BooleanValue source, final HardwareLight light)
   {
      return bind(source, light.isOn());
   }

   public BooleanValueOutputValueBinding bind(
      final BooleanValue source,
      final HardwareControl hardwareControl)
   {
      return bind(source, hardwareControl.backgroundLight());
   }

   public boolean isActive()
   {
      return mIsActive;
   }

   public void setIsActive(final boolean isActive)
   {
      if (isActive != mIsActive)
      {
         mIsActive = isActive;

         mLayers.invalidateBindings();
      }
   }

   public void toggleIsActive()
   {
      setIsActive(!mIsActive);
   }

   private boolean mIsActive;

   private final Layers mLayers;

   final List<Binding> mBindings = new ArrayList<Binding>();

   private final String mName;
}