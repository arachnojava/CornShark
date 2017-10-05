public class CSAIBite extends CSStateBehavior
{
        private CSStateBehavior prevState;

        public CSAIBite(final CSFishBase actor)
        {
            super(actor);
        }

        public CSAIBite(final CSFishBase actor, final CSStateBehavior prev)
        {
            super(actor);
            //actor.setHorizontalSpeed(0);
            //if (actor.getVerticalSpeed() < 0)
            //{
            //    actor.setY(actor.getY() - 10);
            //}

            //actor.setVerticalSpeed(0);

            actor.setFrameNumber(0);
            if (actor.getDirFacing() == CSFishBase.FACING_RIGHT &&
                     actor.getAnimationSequenceNumber() != CSFishBase.ACTION_BITE_RIGHT)
                actor.setAnimationSequence(CSFishBase.ACTION_BITE_RIGHT);
            else if (actor.getDirFacing() == CSFishBase.FACING_LEFT &&
                     actor.getAnimationSequenceNumber() != CSFishBase.ACTION_BITE_LEFT)
                actor.setAnimationSequence(CSFishBase.ACTION_BITE_LEFT);

            if ((actor.getHorizontalSpeed() < 0
                && actor.getDirFacing() == CSFishBase.FACING_RIGHT)
                || (actor.getHorizontalSpeed() > 0
                && actor.getDirFacing() == CSFishBase.FACING_LEFT))
            {
                actor.setHorizontalSpeed(actor.getHorizontalSpeed()*-1);
            }
            else
            {
                final double speed = Math.abs(actor.getHorizontalSpeed() * actor.getAcceleration());
                if (speed <= actor.getMaxHSpeed())
                    actor.setHorizontalSpeed(speed);
                else
                    actor.setHorizontalSpeed(actor.getMaxHSpeed());

                if (actor.getDirFacing() == CSFishBase.FACING_LEFT)
                    actor.setHorizontalSpeed(actor.getHorizontalSpeed() * -1);
            }
            prevState = prev;
        }

        /**
         * @param prevState the prevState to set
         */
        public void setPrevState(final CSStateBehavior prevState)
        {
            this.prevState = prevState;
        }

        @Override
        public void advance()
        {
            final CSFishBase actor = getActor();

            //if (actor.getDrag() < 1)
            //{
            //    actor.setHorizontalSpeed(actor.getHorizontalSpeed() * actor.getDrag());
            //    actor.setVerticalSpeed(actor.getVerticalSpeed() * actor.getDrag());
            //}

            if (actor.getDirFacing() == CSFishBase.FACING_RIGHT &&
                     actor.getAnimationSequenceNumber() != CSFishBase.ACTION_BITE_RIGHT)
                actor.setAnimationSequence(CSFishBase.ACTION_BITE_RIGHT);
            else if (actor.getDirFacing() == CSFishBase.FACING_LEFT &&
                     actor.getAnimationSequenceNumber() != CSFishBase.ACTION_BITE_LEFT)
                actor.setAnimationSequence(CSFishBase.ACTION_BITE_LEFT);

            // if animation is finished, go back to what we were
            // doing before
            if (actor.isAnimationFinished())
            {
                actor.biting = false;
                actor.setStateBehavior(prevState);
            }
        }
}
