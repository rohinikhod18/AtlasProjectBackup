var chartHeight 	= 650,
    chartPadding 	= 50;
var totalBenePayments = 0;

// Create the SVG ==============================
var svg = d3.select('#relationships')
  .append('svg')
  .attr('width','100%')
  .attr('height',chartHeight)
  .append('g')
  .classed('chart',true)
  .attr('transform','scale(1) translate(0,'+chartPadding+')');

var chartWidth = $('svg').width();

// Create the d3 data structure ================

var root = d3.hierarchy(treeData);
root.x0 = chartWidth / 2;
root.y0 = 0;

var treeLayout = d3.tree()
  .size([chartWidth,chartHeight]);

// Collapse after the second level =============

root.children.forEach(collapse);

// Assign colours

var allColours = ['#F4AF34','#F0F434','#61AC1E','#1E86AC','#471EAC','#AC1E84','#BE62D6','#73EC85','#73D0EC','#8D95C1'];
function randomColour() {
  /*var colour = ("000000" + Math.random().toString(16).slice(2, 8).toUpperCase()).slice(-6);
  return '#' + colour;*/
  var colourIndex = Math.floor(Math.random() * allColours.length),
    colour = allColours[colourIndex];

  allColours.splice(colourIndex,1);
  return colour;

}

root.children.forEach(function(c) {
  c.linkColour = randomColour();
});

updateTree(root);

function collapse(d) {
  if(d.children) {
    d._children = d.children;
    d._children.forEach(collapse);
    d.children = null;
  }
}

function populateTable() {

  root.children.forEach(function(c) {

    var payments = c.data.payments;

    payments.forEach(function(p) {

      var tableRow = d3.select('table#tbl-payments tbody').append('tr').classed('row-'+c.depth+'-'+c.id,true),
        tdClient = tableRow.append('td').text(c.data.name),
        tdDate = tableRow.append('td').text(getFormattedDate(p.date)),
        tdRef = tableRow.append('td').text(p.reference),
        tdAmount = tableRow.append('td').classed('number',true).text(p.amount),
        tdCurrency = tableRow.append('td').text(p.currency);
      $("#tbl-bene-div").hide();
      $("#total_benes").hide();

      tdClient.attr('style','border-left:3px solid '+c.linkColour);

    });
  });

}
populateTable();

function getFormattedDate(date){
	console.log(date);
	var date = new Date(date);
	var specifiedDate = date.toLocaleDateString('en-US');
	var specifiedTime = date.toLocaleTimeString();
	
	return specifiedDate + " " + specifiedTime;	
}

// Update the tree =============================

var i = 0, duration = 750;

function updateTree(source) {
  
  var treeData = treeLayout(root);

  // Compute the new tree layout and normalize for fixed depth
  var nodes = treeData.descendants(),
      links = treeData.descendants().slice(1);

  nodes.forEach(function(d) { d.y = d.depth * 200; });

  // Update the nodes
  var node = svg.selectAll('g.node-item')
    .data(nodes, function(d) {return d.id || (d.id = ++i); });

  // Enter any new nodes at the parent's previous position
  var nodeItem =

    node.enter()
    .append('g')
    .classed('node-item',true)
    .attr('id',function(d) {
      return 'node-item-id-'+d.data.id;
    })
    .attr('transform', function(d) {
        return 'translate(' + source.x0 + ',' + source.y0 + ')';
    })
    .on('mouseover', function(d) {

      var theNodeItem = d3.select(this),
        infoPanelSize = 160;

      if (d.data.type == 'client') {
        d3.select('#client-info-tip').classed('hidden',true);
        d3.select('#client-info').classed('hidden',false);
      }

    })
    .on("mouseout", function(d) {

      var theNodeItem = d3.select(this);

      if (d.depth==1) {
        d3.select('#client-info-tip').classed('hidden',false);
        d3.select('#client-info').classed('hidden',true);
      }

    });

  // Create links on the final beneficiaries
  nodeItem
    .append('a')
    .attr("onclick",function(d) {
    	if (d.depth == 2) {
    		return 'history.go(0)';
    	} else {
            return;
          }
     })
    .each(function(d) {

      var thisNode = d3.select(this),
        triangle;

      if (d.data.type == 'client') {

        thisNode.append('path')
        .attr('d','M12.5582894,24.3081779 C12.9007652,24.8864579 13.3834295,25.3691222 13.9617095,25.711598 C15.8625137,26.8373148 18.3159938,26.2089822 19.4417106,24.3081779 L29.6694876,7.03829054 C30.0349501,6.42119689 30.227777,5.71719413 30.227777,5 C30.227777,2.790861 28.436916,1 26.227777,1 L5.77222302,1 C5.05502889,1 4.35102613,1.19282693 3.73393248,1.55828943 C1.83312822,2.68400621 1.20479567,5.13748628 2.33051245,7.03829054 L12.5582894,24.3081779 Z')
        .attr('fill','#fff')
        .attr('style','transform:translate(16px,10px) rotate(180deg)')
        .classed('node',true)
        .classed('node--client',true)
        .classed('node--danger', function(d) {

          var nodeChildren = d.data.children,
            otherParent;

          if (d.depth==1 && nodeChildren) {

            for (var i=0; i < nodeChildren.length; i++) {

              for (var j=0; j < nodeChildren[i].otherParents.length; j++) {

                otherParent = d3.select('.node-item[id="node-item-id-'+nodeChildren[i].otherParents[j].id+'"]');

                d3.select(otherParent._groups[0][0].firstElementChild)
                .classed('node--danger',true);

                otherParent
                .on('click',function() {
                  d3.select('#node-item-id-'+d.data.id+' a').dispatch('click');
                });

                if (d.data.id==nodeChildren[i].otherParents[j].id) {
                  return true;
                }

              }

            }

            return true;

          }

        });

      } else {

        thisNode.append('circle')
        .classed('node',true)
        .classed('node--root',function(d) {
          return d.depth == 0;
        });
      }
    })
    .on('click', click);


  // Create labels
  nodeItem
    .append('text')
    .classed('node-item__text',true)
    .classed('mute',function(d) {
      return d.depth == 2;
    })
    .each(function(d) {

      var thisText = d3.select(this),
          name = d.data.name;

      if (d.depth==2) {
        thisText.attr('transform','rotate(90)');
      }

      thisText
        .text(function(d) {

          if (root.children.length > 5 && name.length > 10 && d.depth==1) {
            name = name.substring(0,10) + '...';
          }

          return name;

        });

      if (name.length>10 && d.depth>0) {
        thisText.attr('data-ot',d.data.name);
      }

    })
    .style('text-anchor', function(d) {
      return d.depth < 2 ? 'middle':'';
    })
    .style('font-weight', function(d) {
      return d.depth ? '':'bold';
    })
    .attr('dx',function(d) {
      return d.depth==2 ? '20px':'0px';
    })
    .attr('dy',function(d) {
       if (d.depth == 0) {
         return '-20px';
       }
       else if (d.depth == 1) {
         return '30px';
       } else {
         return '5px';
       }
    })
    ;

    nodeItem
      .append('a')
      .attr("onclick",function(d) {
    	  var accountid = d.data.id;
    	  var name = d.data.name;
    	  return 'getListOfPayees(event,"'+accountid+'",\''+name+'\')';
      })
      .append('text')
      .text(function(d) {
        var totalBenes = d.data.totalBeneficiaries,
          suffix = totalBenes == 1 ? 'bene':'benes';
        return d.depth == 1 ? '('+d.data.totalBeneficiaries+' '+suffix+')':'';

      })
      .classed('node-item__text--link',true)
      .classed('mute',true)
      .attr('dy','45px')
      .attr('text-anchor','middle');

    var nodeUpdate = nodeItem.merge(node);

    // Transition to the proper position for the node
    nodeUpdate.transition()
      .duration(duration)
      .attr('transform', function(d) {
          return 'translate(' + d.x + ',' + d.y + ')';
       });

    // Update the node attributes and style
    nodeUpdate.select('circle.node')
      .attr('r', 12)
      .attr('cursor', 'pointer');

    // Remove any exiting nodes
    var nodeExit = node.exit().transition()
        .duration(duration)
        .attr('transform', function(d) {
            return 'translate(' + source.y + ',' + source.x + ')';
        })
        .remove();

    // On exit reduce the node circles size to 0
    nodeExit.select('circle')
      .attr('r', 1e-6);

    // On exit reduce the opacity of text labels
    nodeExit.select('text')
      .style('fill-opacity', 1e-6);

    // Update the links
    var link = svg.selectAll('path.link')
        .data(links, function(d) { return d.id; });

    // Enter any new links at the parent's previous position
    var linkEnter = link.enter().insert('path', 'g')
        .attr('id',function(d) {
          return 'path-'+d.depth+'-'+d.id;
        })
        .classed('link', true)
        .classed('link--beneficiary',function(d) {
          return d.depth==1;
        })
        .each(function(d) {

          var thisLink = d3.select(this);

          if (d.depth==1) {
            thisLink.attr('style','stroke:'+d.linkColour);
          }

          if (d.depth==2) {
            thisLink.attr('stroke-dasharray','1,2');
            //thisLink.style('stroke',d.linkColour);
            thisLink.style('stroke','red');
          }

          if (d.depth==1) {
            thisLink.on('click',function() {

              thisLink.classed('link--off') ? thisLink.classed('link--off',false):thisLink.classed('link--off',true);

              if (thisLink.classed('link--off')) {
                d3.selectAll('.row-'+d.depth+'-'+d.id).classed('hidden',true);
              } else {
                d3.selectAll('.row-'+d.depth+'-'+d.id).classed('hidden',false);
              }

            });
          }

        })
        .attr('d', d3.linkHorizontal()
          .x(function(d) { return source.x; })
          .y(function(d) { return source.y; }));

    // UPDATE
    var linkUpdate = linkEnter.merge(link);

    // Transition back to the parent element position
    linkUpdate.transition()
        .duration(duration)
        .attr('d', function(d) { return diagonal(d, d.parent); })
        .on('end',function(d) {

         var pN, pLinkColour;

         if (d.data.otherParents) {

          //pLinkColour = randomColour();

          for (var i=0;i<d.data.otherParents.length;i++) {

            pN = d3.select('.node-item[id="node-item-id-'+d.data.otherParents[i].id+'"]');

            root.each(function(node) {
              if (node.data.id==d.data.otherParents[i].id) {
                pN = node;
              }
            });

            var dangerPathGroup = svg.append('g')
            .attr('id',function() {
              return 'danger-path-group-'+d.depth+'-'+d.id;
            });

            dangerPathGroup
              .append('path')
              .attr('d', function() { return diagonal(d, pN) })
              .attr('id',function() {
                return 'danger-path-'+d.depth+'-'+d.data.otherParents[i].id;
              })
              .attr('style','stroke:red;stroke-width:2;fill:none')
              .attr('stroke-dasharray','1,2');

            dangerPathGroup
              .append('text')
              .classed('link-text--danger',true)
              .classed('mute',true)
              .attr('text-anchor','middle')
              .attr('dy','-5px')
              .append('textPath')
              .attr('xlink:href',function() {
                return '#danger-path-'+d.depth+'-'+d.data.otherParents[i].id;
              })
              .text(function() {

                var pNArray = d.data.otherParents.filter(function(el) {
                  return el.id == d.data.otherParents[i].id;
                });

                return pNArray[0].noPayments;

              })
              .attr('startOffset','50%');

            }

         }

       });

    // Remove any exiting links
    var linkExit = link.exit().transition()
        .duration(duration)
        .attr('d', d3.linkHorizontal()
          .x(function(d) { return source.x; })
          .y(function(d) { return source.y; }))
        .remove()
        .on('start',function(d) {
          d3.select('#danger-path-group-'+d.depth+'-'+d.id).remove();
        });

    // Insert link text
    var linkText = svg.selectAll('text.link-text')
      .data(nodes, function(d) {  return d.id; });

    var linkTextEnter = linkText.enter().insert('text','g')
    .classed('link-text',true)
    .classed('mute',true)
    .attr('text-anchor','middle')
    .attr('dy','-5px');

    linkTextEnter.append('textPath')
      .attr('xlink:href',function(d) {
        return '#path-'+d.depth+'-'+d.id;
      })
      .text(function(d) {

        if (d.depth == 1) {
          if (d.data.payments) {
            totalBenePayments += d.data.payments.length;
            return d.data.payments.length;
          } else {
            return '';
          }
          //return d.data.payments ? d.data.payments.length:'';
        }

        if (d.depth == 2) {
          return d.data.noPayments;
        }

      })
      .attr('startOffset','50%');

    $('#total-no-payments,#total-no--payments').text(totalBenePayments);

    // Creates a curved (diagonal) path from parent to the child nodes
    function diagonal(s, d) {

      path = `M ${s.x} ${s.y}
              C ${(s.x + d.x) / 2} ${s.y},
                ${(s.x + d.x) / 2} ${d.y},
                ${d.x} ${d.y}`;

      return path;
    }

    // Store the old positions for transition.
    nodes.forEach(function(d){
      d.x0 = d.x;
      d.y0 = d.y;
    });

    // Toggle children on click.
    function click(d) {

      if (d.depth > 0) {

        if (d.children) {
            d._children = d.children;
            d.children = null;
          } else {
            d.children = d._children;
            d._children = null;
          }
        updateTree(d);
      }

    }

}
