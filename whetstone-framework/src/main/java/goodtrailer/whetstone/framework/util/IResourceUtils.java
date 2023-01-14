package goodtrailer.whetstone.framework.util;

import java.io.InputStream;

import javax.swing.ImageIcon;

public interface IResourceUtils
{
    static ImageIcon getImage(Class<?> origin, String path)
    {
        var resource = origin.getResource(path);
        if (resource == null)
            return null;
        return new ImageIcon(resource);
    }

    static ImageIcon getImage(Object origin, String path)
    { return getImage(origin.getClass(), path); }

    static InputStream getFile(Class<?> origin, String path)
    { return origin.getResourceAsStream(path); }

    static InputStream getFile(Object origin, String path)
    { return getFile(origin.getClass(), path); }
}
