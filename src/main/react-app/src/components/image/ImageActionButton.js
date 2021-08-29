import PropTypes from 'prop-types'
import {AiOutlineDelete, AiOutlineDollar, AiOutlineDownload, AiOutlineEdit} from 'react-icons/all'


const ImageActionButton = ({type, size}) => {
  switch (type) {
    case 'owner':
      return (
        <div>
          <AiOutlineDownload size={size} />
          <AiOutlineEdit size={size} />
          <AiOutlineDelete color='red' size={size} />
        </div>
      )
    case 'bought':
      return <AiOutlineDownload size={size} />
    default:
      return <AiOutlineDollar size={size} />
  }
}

ImageActionButton.propTypes = {
  type: PropTypes.oneOf(['OWNER','BOUGHT','NONE']),
  size: PropTypes.string
}

ImageActionButton.defaultProps = {
  type: 'none',
  size: '1.2em'
}

export default ImageActionButton