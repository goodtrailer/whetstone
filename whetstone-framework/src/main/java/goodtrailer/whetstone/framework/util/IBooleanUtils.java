package goodtrailer.whetstone.framework.util;

import java.util.concurrent.ThreadLocalRandom;

public interface IBooleanUtils
{
    static boolean random()
    {
        return ThreadLocalRandom.current().nextInt(2) == 1;
    }
}
