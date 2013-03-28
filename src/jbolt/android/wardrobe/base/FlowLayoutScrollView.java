package jbolt.android.wardrobe.base;


import android.content.Context; 
import android.os.Handler; 
import android.os.Message; 
import android.util.AttributeSet; 
import android.view.MotionEvent; 
import android.view.View; 
import android.widget.ScrollView; 



public class FlowLayoutScrollView extends ScrollView {
	
	protected static final String tag="FlowLayoutScrollView";
	  
	private Handler handler; 
	private View view; 
	private OnScrollListener onScrollListener; 
	  
	public FlowLayoutScrollView(Context context) {
		super(context); 
		// TODO Auto-generated constructor stub 
	} 
	  
	public FlowLayoutScrollView(Context context, AttributeSet attrs) {
		super(context, attrs); 
		// TODO Auto-generated constructor stub 
	} 
	  
	public FlowLayoutScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle); 
		// TODO Auto-generated constructor stub 
	} 
	  
	public int computeVerticalScrollRange(){ 
		return super.computeVerticalScrollRange(); 
	} 
	  
	public int computeVerticalScrollOffset(){ 
		return super.computeVerticalScrollOffset(); 
	} 
	  
	private void init(){ 
		this.setOnTouchListener(onTouchListener); 
		handler = new Handler(){ 
			@Override 
			public void handleMessage(Message msg){ 
				super.handleMessage(msg); 
				switch (msg.what) { 
				case 1: 
					if (view.getMeasuredHeight() <= getScrollY()+getHeight()){ 
						if (onScrollListener != null){ 
							onScrollListener.onBottom();        
						}       
					}else if (getScrollY() == 0){ 
						if (onScrollListener != null){ 
							onScrollListener.onTop(); 
						} 
					}else{ 
						if (onScrollListener != null){ 
							onScrollListener.onScroll(); 
						} 
					} 
					break; 
				default: 
					break; 
				} 
	     
			} 	    
		};   
	   
	} 
	  
	OnTouchListener onTouchListener = new OnTouchListener(){ 
	   
		@Override 
		public boolean onTouch(View v, MotionEvent event) { 
			// TODO Auto-generated method stub 
			switch (event.getAction()){ 
			case MotionEvent.ACTION_DOWN: 
				break; 
			case MotionEvent.ACTION_UP: 
				if(view !=null && onScrollListener != null){ 
					handler.sendMessageDelayed(handler.obtainMessage(1), 200); 
				} 
				break; 
			} 
			return false; 
		} 
	}; 
	   
	public interface OnScrollListener{ 
		void onBottom(); 
		void onTop(); 
		void onScroll(); 
	   
	} 
	  
	public void getView(){ 
		this.view=getChildAt(0); 
		if (view != null){ 
			init(); 
		}    
	} 
	  
	public void setOnScrollListener(OnScrollListener listener){ 
		this.onScrollListener = listener; 
	}
}
