/**
产品列表
**/

var ProductRow = React.createClass({displayName: "ProductRow",
	getInitialState: function() {
          return {value: 'Hello!'};
    },
    handler_click_detail:function(e){

    	React.render(React.createElement(Product, {data: this.props.product}), document.getElementById("ajax-content"));
    	return false;
    },
    componentDidMount: function() {
    	asyn_img();
    },
    render: function() {
		
		var divStyle1 = {'textAlign':'left',width:'60%',float:'left'};
		var divStyle2 = {'textAlign':'right',width:'40%',float:'right'};

		var product_url="ajax/product-"+this.props.product.id+".html";
		var img_url="product-img-"+this.props.product.id+".action";

      return (React.createElement("tr", null, 
        React.createElement("td", null, 
          React.createElement("img", {src: "resources/images/empty.jpg", value: img_url, className: "product-img", alt: "产品图片"})
        ), 
        React.createElement("td", null, 
          React.createElement("ul", {className: "list-unstyled"}, 
            React.createElement("li", null, React.createElement("a", {className: "ajax-link", href: product_url, key: this.props.product.id}, this.props.product.title), 
				this.props.product.keywords.substr(0,24)
            ), 
    
            React.createElement("li", null, 
              React.createElement("div", {style: divStyle1}, React.createElement("s", null, "￥", this.props.product.price_normal), " ", React.createElement("span", {className: "product-price"}, "￥", this.props.product.price_now)), 
              React.createElement("div", {style: divStyle2}, 
                React.createElement("span", {className: "label label-info"})
              )
            )
          )
          
        )
      ));

    }
});


var ProductTable = React.createClass({displayName: "ProductTable",
	getInitialState: function() {
          return {
          		value: 'Hello!',
          		title:'',
          		categroryName:'',
          		page:1,
          		limit:15,
          		rows:[]
          };
    },    
    componentWillReceiveProps: function(nextProps) {
    	
    	if (nextProps.page != this.props.page) {
    		this.loadData(nextProps.page);
    	}
  },  
    componentDidMount: function() {
    	
        this.loadData(1,this.state.limit);
   },
   loadData: function(page,limit) {
   
	var title=$('#search_key_title').text();
	var cate=$('#search_key_cate').text();
	
	var dealerId=$('#search_dealer_id').val();
	
    $.get('search.json',
			{ page: page, limit: limit,title:title,cateName:cate ,dealerId:dealerId} 
    	).done(function(data) {
      		console.log("Received data");
      		this.setState({	
      					rows: this.state.rows.concat(data.rows) ,
          				count: data.count 
          			});
      		
      		if(data.rows.length==limit){
      			
      			$('.pager-more').show();
      		}else{
      			$('.pager-more').hide();
      		}
      		
      		//this.forceUpdate();
    }.bind(this))
     .fail(function(error, a, b) {
      console.log("Error loading JSON");
    });

  },
  handler_click_more:function(event){
        
        this.loadData(this.state.page+1,this.state.limit);
        this.setState({page:this.state.page+1});
    },
    render: function() {
        
        var rows = [];
        
        this.state.rows.forEach(function(product) {
            
            rows.push(React.createElement(ProductRow, {product: product, key: product.id}));            
        });

            if(rows.length==0){
            	
            	rows.push(React.createElement("tr", null, 
            		React.createElement("td", {colSpan: "2"}, React.createElement("small", null, "抱歉，暂无数据"))
            	) );
            }
            
        return (
            React.createElement("table", {className: "products-table"}, 
                rows, 
  				React.createElement("tfoot", null, 
         			React.createElement("tr", null, 
            			React.createElement("td", {colSpan: "2", className: "pager-more"}, React.createElement("a", {href: "#", onClick: this.handler_click_more}, "点击加载更多"))
         			)
      			)
            )
        );
    }
});

React.render(React.createElement(ProductTable, {title: "", cate: ""}), document.getElementById("products-placehold"));