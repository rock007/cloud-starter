/**
订单列表
**/

var OrderRow = React.createClass({displayName: "OrderRow",
	getInitialState: function() {
          return {value: 'Hello!'};
    },
    componentDidMount: function() {
    	
    	asyn_img();
    },
    render: function() {
		
		var divStyle1 = {'textAlign':'left',width:'60%',float:'left'};
		var divStyle2 = {'textAlign':'right',width:'40%',float:'right'};

		var img_url="resources/images/empty.jpg";

	 	img_url="product-img-"+this.props.data.orderProduct.productId+".action";
		
		var product_url="ajax/order-"+this.props.data.id+".html";

      return (React.createElement("tr", null, 
        React.createElement("td", null, 
          React.createElement("img", {src: "resources/images/empty.jpg", className: "product-img", value: img_url, alt: "产品图片"})
        ), 
        React.createElement("td", null, 
          React.createElement("ul", {className: "list-unstyled"}, 
            React.createElement("li", null, 
            React.createElement("a", {className: "ajax-link", href: product_url, data: this.props.data.id}, this.props.data.orderProduct.title)
            ), 
            React.createElement("li", null, 
            React.createElement("small", null, 
            this.props.data.orderProduct.attr_value
            )
            ), 
    
            React.createElement("li", null, 
              React.createElement("div", {style: divStyle1}, 
              React.createElement("s", null, "￥", this.props.data.orderProduct.price_normal), " ", 
              React.createElement("span", {className: "product-price"}, 
              "￥", this.props.data.price)
              ), 
              React.createElement("div", {style: divStyle2}, 
                React.createElement("span", {className: "label label-primary"}, this.formatOrderStatus( this.props.data.orderStatus))
              )
            )
          )
          
        )
      ));

    },
    formatOrderStatus:function(status){
    	
    	var msg='';
    	
    	if(status=='cancel'){
    		msg='过期取消';
    	}else if(status=='nopay'){
    		msg='未支付';
    	}else if(status=='pay'){
    		msg='支付完成';
    	}else if(status=='packonline'){
    		msg='已发货';
    	}else if(status=='packarrive'){
    		msg='签收';
    	}else if(status=='reqback'){
    		msg='退货/退款';
    	}else if(status=='backdone'){
    		msg='退货完成';
    	}else if(status=='backcancel'){
    		msg='退货取消';
    	}else if(status=='confirmdeal'){
    		msg='确认收货';
    	}else if(status=='dealdone'){
    		msg='交易完成';
    	}
    	
    	return msg;
    	
    }
});


var OrdersTable = React.createClass({displayName: "OrdersTable",
	getInitialState: function() {
          return {
          		status:0,
          		page:1,
          		limit:15,
          		rows:[]
          };
    },    
    componentWillReceiveProps: function(nextProps) {
   
    if (nextProps.status != this.props.status) {
      	this.loadData(1,this.state.limit,nextProps.status);
    }
  }, 
    componentDidMount: function() {
    	
        this.loadData(1,this.state.limit,0);
        var me=this;
        var tabs=$(React.findDOMNode(this.refs.myTab)).children('li');
        tabs.click(function(v){
        	
        	tabs.removeClass('active');
    		$(this).addClass("active");
    		
    		me.setState({status:$(this).attr('data')});
    		me.loadData(1,me.state.limit,me.state.status);
        });
        
   },
   loadData: function(page,limit,status) {
   
    $.get('orders.json',
			{ page: page, limit: limit,status:status } 
    	).done(function(data) {
      		console.log("Received data");

      		if(page > 1){
          		this.setState({	
  					rows: this.state.rows.concat(data.rows) ,
      				count: data.count 
      			});      			
      		}else{
          		this.setState({	
  					rows: data.rows ,
      				count: data.count 
      			});
      		}
      		
      		if(data.rows.length==limit){
      			
      			$('.pager-more').show();
      		}else{
      			$('.pager-more').hide();
      		}
      		
    }.bind(this))
     .fail(function(error, a, b) {
      console.log("Error loading JSON");
    });

  },
  handler_click_more:function(event){
        
        this.loadData(this.state.page+1,this.state.limit,this.state.status);
        this.setState({page:this.state.page+1});
    },
    render: function() {
        
        var rows = [];
        
        this.state.rows.forEach(function(item) {
            
            rows.push(React.createElement(OrderRow, {data: item}));            
        });

        return (
        	React.createElement("div", null, 
        		React.createElement("div", {className: "row"}, 
        		  React.createElement("div", {className: "col-xs-12"}, 
        		     React.createElement("ul", {ref: "myTab", className: "nav nav-pills", role: "tablist"}, 
        		      React.createElement("li", {role: "presentation", className: "active", data: "0"}, React.createElement("a", {href: "#", role: "tab"}, "未完成")), 
        		      React.createElement("li", {role: "presentation", className: "", data: "9"}, React.createElement("a", {href: "#", role: "tab"}, "完成")), 
        		      React.createElement("li", {role: "presentation", className: "", data: "100"}, React.createElement("a", {href: "#", role: "tab"}, "全部"))
        		      
        		    )
        		   )
        		), 

        		React.createElement("div", {className: "row"}, 
        		  React.createElement("div", {className: "col-xs-12 fit-size"}, 
        		  	React.createElement("table", {className: "products-table"}, 
        		  		rows, 
        		  	React.createElement("tfoot", null, 
        		  		React.createElement("tr", null, 
            				React.createElement("td", {colSpan: "2", className: "pager-more"}, React.createElement("a", {href: "#", onClick: this.handler_click_more}, "点击加载更多"))
            			)
         			)
         			)
         		)
               )
            
            )
        );
    }
});

React.render(React.createElement(OrdersTable, null), document.getElementById("orders_placehold"));