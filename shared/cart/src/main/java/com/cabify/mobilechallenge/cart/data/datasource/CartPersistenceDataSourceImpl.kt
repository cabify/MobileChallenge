import com.cabify.mobilechallenge.cart.data.datasource.CartPersistenceDataSource
import com.cabify.mobilechallenge.cart.data.mapper.CartItemDataMapper
import com.cabify.mobilechallenge.cart.data.mapper.CartItemsDataMapper
import com.cabify.mobilechallenge.cart.data.mapper.CartItemEntityMapper
import com.cabify.mobilechallenge.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.core.base.mapper.Mapper
import com.cabify.mobilechallenge.persistence.dao.CartDAO
import com.cabify.mobilechallenge.persistence.entity.CartItemData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

class CartPersistenceDataSourceImpl(
    private val cartDao: CartDAO<CartItemData>,
    private val cartItemDataMapper: CartItemDataMapper,
    private val cartItemsDataMapper: CartItemsDataMapper,
    private val cartItemEntityMapper: CartItemEntityMapper
) :
    CartPersistenceDataSource {

    override fun changes(): Observable<CartEntity> =
        cartDao.changes().map(cartItemsDataMapper::map)

    override fun upsertQuantity(item: CartEntity.Item): Completable =
        cartDao.upsertQuantity(cartItemEntityMapper.map(item))

    override fun deleteCart(): Completable =
        cartDao.delete()
}
