package resttopoly.services;

import lombok.Data;

/**
 * @author DucNguyenMinh
 * @since 02/05/16
 */
@Data
public class YellowpagesService
{
    private String _uri;
    private String uri;
    private String status;
    private String name;
    private String description;
    private String service;

    public String get_uri()
    {
        return _uri;
    }

    public String getUri()
    {
        return uri;
    }

    public String getStatus()
    {
        return status;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public String getService()
    {
        return service;
    }
}
