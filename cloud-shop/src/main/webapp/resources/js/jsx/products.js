/**
产品列表
**/

var ProductRow = React.createClass({
	getInitialState: function() {
          return {value: 'Hello!'};
    },
    handler_click_detail:function(e){

    	React.render(<Product  data={this.props.product}/>, document.getElementById("ajax-content"));
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

      return (<tr>
        <td>
          <img src="resources/images/empty.jpg" value={img_url} className="product-img" alt="产品图片"></img>
        </td>
        <td>
          <ul className="list-unstyled">
            <li><a className="ajax-link"  href={product_url}   key={this.props.product.id}>{this.props.product.title}</a>
				{this.props.product.keywords.substr(0,24)}
            </li>
    
            <li>
              <div  style={divStyle1}><s>￥{this.props.product.price_normal}</s>&nbsp;<span className="product-price">￥{this.props.product.price_now}</span></div>
              <div style={divStyle2}>
                <span className="label label-info"></span> 
              </div>
            </li>
          </ul>
          
        </td>
      </tr>);

    }
});


var ProductTable = React.createClass({
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
            
            rows.push(<ProductRow product={product} key={product.id} />);            
        });

            if(rows.length==0){
            	
            	rows.push(<tr> 
            		<td colSpan="2"><small>抱歉，暂无数据</small></td>
            	</tr> );
            }
            
        return (
            <table className="products-table">
                {rows}
  				<tfoot>
         			<tr >
            			<td colSpan="2" className="pager-more"><a href="#" onClick={this.handler_click_more}>点击加载更多</a></td>
         			</tr>
      			</tfoot>
            </table>
        );
    }
});

React.render(<ProductTable title="" cate="" />, document.getElementById("products-placehold"));