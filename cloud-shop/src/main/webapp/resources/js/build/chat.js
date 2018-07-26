/**
咨询
**/

var MsgDateRec = React.createClass({displayName: "MsgDateRec",
    render: function() {
      return (
    		  React.createElement("li", {className: "text-center"}, React.createElement("p", {className: "text-muted"}, " ", this.props.data, " "), " ")
  		    );

    }
});

var OneFromOtherMsg = React.createClass({displayName: "OneFromOtherMsg",
    render: function() {
      return (
    		  React.createElement("li", {className: "chat-seller"}, React.createElement("span", {className: "seller"}, "卖家"), "：", this.props.data.content)
  		    );

    }
});

var OneFromMeMsg = React.createClass({displayName: "OneFromMeMsg",
    render: function() {
      return (
  		      React.createElement("li", {className: "chat-me"}, this.props.data.content, " ：", React.createElement("span", {className: "me"}, "我"), " ")
  		    );

    }
});


var MsgView = React.createClass({displayName: "MsgView",
	getInitialState: function() {
          return {          		
          		page:1,
          		limit:15,
          		rows:[]
          };
    },    
    componentWillReceiveProps: function(nextProps) {
   
    if (nextProps.gid != this.props.gid) {
      	this.loadData(1,this.state.limit,nextProps.gid);
    }
  }, 
    componentDidMount: function() {
    	
    	if (this.isMounted()) {
    		
    		var height=$('#main').height()-120;
        	
        	$(React.findDOMNode(this.refs.chat_content)).height(height);
        	$(React.findDOMNode(this.refs.chat_content)).css('overflow', 'auto');
        	
        	$(React.findDOMNode(this.refs.chat_content_hold)).height(height-10);
        	$(React.findDOMNode(this.refs.chat_content_hold)).css('overflow', 'auto');
        	
            this.loadData(1,this.state.limit,this.props.gid);	
    	}
    	
   },
   loadData: function(page,limit,gid) {
   
    $.get('msgs.json',
			{ page: page, limit: limit,groupId:gid } 
    	).done(function(data) {
    		
      		console.log("Received data");
      		
      		if(page > 1){
      			
      			for(var i=0;i<data.rows.length;i++){
      				
      				if(i==0){
      					
      					this.state.rows.unshift(data.rows[i].create_date);
      					
      					this.state.rows.unshift(data.rows[i]);	
      				}else{
      					this.state.rows.unshift(data.rows[i]);
      				}
      				
      			}
      			
          		this.setState({	
  					rows: this.state.rows ,
      				count: data.count 
      			});      			
      		}else{
          		this.setState({	
  					rows: data.rows.reverse(),
      				count: data.count 
      			});
      		}
      		if(data.rows.length==limit){
      			
      			$('.chat-more').show();
      		}else{
      			$('.chat-more').hide();
      		}
      		
    }.bind(this))
     .fail(function(error, a, b) {
      console.log("Error loading JSON");
    });

  },
  handler_click_more:function(event){
        
        this.loadData(this.state.page+1,this.state.limit,this.props.gid );
        this.setState({page:this.state.page+1});
    },
    handleSubmit:function(event){
    	var me=this;
    	
    	var form=$(React.findDOMNode(this.refs.chat_form));
    	
    	var content=$(React.findDOMNode(this.refs.msg)).val();
   	  
   	  	if(content==""){
   		  
   	  		message('消息不能为空');
   		  
   	  		return false;
   	  	}

   	  $.ajax({
               url: form.attr('action'),
               type: 'POST',
               data: form.serialize(),
               success: function(result) {
               	console.log(result);

                 if(result.success){
                	   
                	   $(React.findDOMNode(me.refs.msg)).val("");
                	 
                	   //!!!$(React.findDOMNode(me.refs.gid)).val(result.data.groupId);
                	   
                	   me.state.rows.push(result.data);
                	   
                	   me.setState({	
         					rows: me.state.rows  
             			});
                	   
                   }else{
                   	 message(result.msg);
                   }
               }
           });
   	  
   	  return false;
   
    },
    render: function() {
        
		var divStyle1 = {'textAlign':'right',width:'70%',float:'left','vertical-align':'middle'};
		var divStyle2 = {'textAlign':'left',width:'30%',float:'right'};
    	
        var rows = [];
        var me=this;
        this.state.rows.forEach(function(item) {
            
        	if( typeof(item) == "string"){
        		
        		rows.push(React.createElement(MsgDateRec, {data: item})); 
        				
        	}else if(typeof(item) == "object"){
        	
        		if(item.from_user==me.props.fromUser){
            		
            		rows.push(React.createElement(OneFromMeMsg, {data: item}));  	
            	}else {
            		
            		rows.push(React.createElement(OneFromOtherMsg, {data: item}));  
            	}
        	}
        	         
        });

        return (
        React.createElement("div", null, 	
        React.createElement("div", {className: "row", ref: "chat_content", id: "chat_content"}, 
        	React.createElement("div", {className: "col-xs-12", ref: "chat_content_hold", id: "chat_content_hold"}, 
        
        		   React.createElement("ul", {className: "list-unstyled"}, 
        		    React.createElement("li", {className: "chat-more"}, " ", React.createElement("a", {href: "javascript:void(0)", onClick: this.handler_click_more}, "点击加载更多"), " "), 
        		    rows
        		  )
            )
        ), 
        React.createElement("div", {className: "clear"}), 
        React.createElement("div", {className: "chat-box"}, 
        	React.createElement("form", {ref: "chat_form", role: "form", action: "post-msg.action", method: "post", onSubmit: this.handleSubmit}, 
        		React.createElement("input", {type: "hidden", name: "gid", ref: "gid", value: me.props.gid}), 
        		React.createElement("input", {type: "hidden", name: "to", ref: "to", value: me.props.toUser}), 
       	
        		React.createElement("div", {style: divStyle1}, 
                	React.createElement("textarea", {className: "form-control", maxLength: "150", rows: "1", ref: "msg", name: "msg", placeholder: "输入想要说的话"})
                ), 
                React.createElement("div", {style: divStyle2}, 
                	React.createElement("button", {type: "submit", className: "btn btn-default"}, "发送")
                )
            )
    	)
      )
        		
        );
    }
});

//React.render(<MsgView toUser="" fromUser="hello" gid="" />, document.getElementById("msg_placehoid"));