import PropTypes from 'prop-types'

const Hashtag = ({value}) => {
  return (
    <span className='p-1 me-1 small rounded-3' style={{background: 'cyan'}}>
      {value}
    </span>
  )
}

Hashtag.propTypes = {
  value: PropTypes.string.isRequired
}

export default Hashtag