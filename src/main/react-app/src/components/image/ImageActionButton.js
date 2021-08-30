import PropTypes from 'prop-types'
import {AiOutlineDelete, AiOutlineDollar, AiOutlineDownload, AiOutlineEdit} from 'react-icons/all'


const ImageActionButton = ({type, size}) => {
  switch (type) {
    case 'owner':
      return (
        <div>
          <AiOutlineDownload size={size} cursor="pointer"/>
          <AiOutlineEdit size={size} cursor="pointer"/>
          <AiOutlineDelete color="red" size={size} cursor="pointer"/>
        </div>
      )
    case 'bought':
      return <AiOutlineDownload size={size} cursor="pointer"/>
    default:
      return <AiOutlineDollar size={size} cursor="pointer"/>
  }
}

ImageActionButton.propTypes = {
  type: PropTypes.oneOf(['OWNER', 'BOUGHT', 'NONE']),
  size: PropTypes.string
}

ImageActionButton.defaultProps = {
  type: 'none',
  size: '1.2em'
}

export default ImageActionButton