package cc.before30.home.ex.eventstorm.products.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * ProductChanged
 *
 * @author before30
 * @since 2019-08-04
 */

@Getter
@Setter
public class ProductChanged {
    private String type ;
    private String stateMessage = "상품 변경이 발생함";
    private Long productId;
    private String productName;
    private int productPrice;
    private int productStock;

    public ProductChanged(){
        this.setType(this.getClass().getSimpleName());
    }


}
