package com.bitwig.extensions.controllers.arturia.keylab.mkii;

import com.bitwig.extension.api.util.midi.ShortMidiMessage;
import com.bitwig.extensions.framework.targets.ButtonTarget;

public abstract class AbstractButton
{
   public AbstractButton(final Buttons buttonID)
   {
      mButtonID = buttonID;
   }

   public void onMidi(final ButtonTarget target, final ShortMidiMessage data)
   {
      if (data.isNoteOn() && data.getChannel() == 0)
      {
         final boolean on = data.getData2() >= 64;
         final int key = data.getData1();

         if (key == mButtonID.getKey())
         {
            target.set(on);
         }
      }
   }

   protected final Buttons mButtonID;
}