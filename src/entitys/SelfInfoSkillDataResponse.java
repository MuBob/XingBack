package entitys;

import java.util.Date;

public class SelfInfoSkillDataResponse extends BaseDataResponse{
	    /**
	 * 
	 */
	private static final long serialVersionUID = 8901239358437203242L;
		private String id;
	    private String skillNeme;
	    private String skillLevel;
	    
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getSkillNeme() {
			return skillNeme;
		}
		public void setSkillNeme(String skillNeme) {
			this.skillNeme = skillNeme;
		}
		public String getSkillLevel() {
			return skillLevel;
		}
		public void setSkillLevel(String skillLevel) {
			this.skillLevel = skillLevel;
		}
		@Override
		public String toString() {
			return "SelfInfoSkillDataResponse [id=" + id + ", skillNeme=" + skillNeme + ", skillLevel=" + skillLevel
					+ "]";
		}
	    
	    
}
