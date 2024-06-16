package com.airfrance.repind.dto.channel;

/*PROTECTED REGION ID(__hJpIN8IEeWpIP6zY_fX5w i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : ChannelToCheckDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ChannelToCheckDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = 715765941918332L;


	/**
     * id
     */
    private Integer id;
        
        
    /**
     * channel
     */
    private String channel;
        

    /*PROTECTED REGION ID(__hJpIN8IEeWpIP6zY_fX5w u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public ChannelToCheckDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pId id
     * @param pChannel channel
     */
    public ChannelToCheckDTO(Integer pId, String pChannel) {
        this.id = pId;
        this.channel = pChannel;
    }

    /**
     *
     * @return channel
     */
    public String getChannel() {
        return this.channel;
    }

    /**
     *
     * @param pChannel channel value
     */
    public void setChannel(String pChannel) {
        this.channel = pChannel;
    }

    /**
     *
     * @return id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     *
     * @param pId id value
     */
    public void setId(Integer pId) {
        this.id = pId;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString__hJpIN8IEeWpIP6zY_fX5w) ENABLED START*/
        result = toStringImpl();
        /*PROTECTED REGION END*/
        return result;
    }

    /**
     *
     * @return object as string
     */
    public String toStringImpl() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName());
        buffer.append("@").append(Integer.toHexString(System.identityHashCode(this)));
        buffer.append("[");
        buffer.append("id=").append(getId());
        buffer.append(",");
        buffer.append("channel=").append(getChannel());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(__hJpIN8IEeWpIP6zY_fX5w u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
